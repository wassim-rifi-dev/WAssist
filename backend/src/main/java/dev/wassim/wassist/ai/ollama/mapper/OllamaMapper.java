package dev.wassim.wassist.ai.ollama.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.wassim.wassist.ai.ollama.dto.OllamaMessage;
import dev.wassim.wassist.ai.ollama.dto.request.OllamaChatRequest;
import dev.wassim.wassist.domain.conversation.Conversation;
import dev.wassim.wassist.domain.dto.AgentMessage;
import dev.wassim.wassist.domain.dto.response.AgentResponse;
import dev.wassim.wassist.domain.enums.MessageRoles;
import lombok.RequiredArgsConstructor;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class OllamaMapper {
    private final ObjectMapper objectMapper;

    @Value("${ollama.model}")
    private String model;

    private static final String SYSTEM_PROMPT = """
            You are an AI Agent.

            You must always respond using JSON only.

            Allowed responses:

            MESSAGE:
            {
                "type": "MESSAGE",
                "message": "your answer"
            }

            TOOL_CALL:
            {
                "type": "TOOL_CALL",
                "toolCall": {
                    "tool": "read_file",
                    "arguments": {
                        "path": "file path"
                    }
                }
            }

            Rules:
            - Never return normal text.
            - Never use markdown.
            - Never add explanations outside JSON.
            - Use TOOL_CALL when you need a tool.
            - Use MESSAGE when you can answer directly.


            Available tools:

            read_file:
            Reads a text file from the workspace.

            Arguments:
            {
                "path": "string"
            }
            """;

    private String mapRole(MessageRoles role) {
        return switch (role) {
            case SYSTEM -> "system";
            case USER -> "user";
            case ASSISTANT -> "assistant";
            case TOOL -> "tool";
        };
    }

    public OllamaMessage toOllamaMessage(AgentMessage message) {
        return new OllamaMessage(
            mapRole(message.getRole()),
            message.getContent()
        );
    }

    public OllamaChatRequest toOllamaChatRequest(Conversation conversation) {

        List<OllamaMessage> messages = conversation.getMessages().stream().map(this::toOllamaMessage).toList();


        return new OllamaChatRequest(
                model,
                messages,
                false
        );
    }

    public AgentResponse toAgentResponse(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalStateException(
                "Ollama returned empty JSON for AgentResponse"
            );
        }

        try {
            return objectMapper.readValue(
                    content,
                    AgentResponse.class
            );

        } catch (JacksonException exception) {
            throw new IllegalStateException(
                "Ollama returned invalid JSON for AgentResponse",
                exception
            );
        }
    }
}
