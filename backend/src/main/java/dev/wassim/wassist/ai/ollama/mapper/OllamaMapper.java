package dev.wassim.wassist.ai.ollama.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.wassim.wassist.ai.ollama.dto.OllamaMessage;
import dev.wassim.wassist.ai.ollama.dto.request.OllamaChatRequest;
import dev.wassim.wassist.domain.dto.response.AgentResponse;
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

    public OllamaMessage toSystemMessage() {
        return new OllamaMessage(
                "system",
                SYSTEM_PROMPT
        );
    }

    public OllamaMessage toOllamaMessage(String prompt) {
        return new OllamaMessage(
            "user",
            prompt
        );
    }

    public OllamaChatRequest toOllamaChatRequest(String prompt) {
        OllamaMessage systemMessage = toSystemMessage();

        OllamaMessage userMessage = toOllamaMessage(prompt);


        return new OllamaChatRequest(
                model,
                List.of(
                    systemMessage,
                    userMessage
                ),
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
