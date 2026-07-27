package dev.wassim.wassist.ai.ollama.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.wassim.wassist.ai.ollama.dto.OllamaMessage;
import dev.wassim.wassist.ai.ollama.dto.request.OllamaChatRequest;
import dev.wassim.wassist.ai.ollama.utils.RoleMap;
import dev.wassim.wassist.domain.conversation.Conversation;
import dev.wassim.wassist.domain.dto.AgentMessage;
import dev.wassim.wassist.domain.dto.response.AgentResponse;
import lombok.RequiredArgsConstructor;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class OllamaMapper {
    private final ObjectMapper objectMapper;
    private final RoleMap roleMap;

    @Value("${ollama.model}")
    private String model;

    public OllamaMessage toOllamaMessage(AgentMessage message) {
        return new OllamaMessage(
            roleMap.mapRole(message.getRole()),
            message.getContent(),
            message.getToolCall(),
            message.getToolName()
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
