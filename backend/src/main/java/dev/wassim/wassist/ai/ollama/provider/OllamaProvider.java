package dev.wassim.wassist.ai.ollama.provider;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.ai.provider.AIProvider;
import dev.wassim.wassist.domain.conversation.Conversation;
import dev.wassim.wassist.domain.dto.response.AgentResponse;
import dev.wassim.wassist.ai.ollama.client.OllamaClient;
import dev.wassim.wassist.ai.ollama.dto.request.OllamaChatRequest;
import dev.wassim.wassist.ai.ollama.dto.response.OllamaChatResponse;
import dev.wassim.wassist.ai.ollama.mapper.OllamaMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OllamaProvider implements AIProvider {
        private final OllamaClient ollamaClient;
        private final OllamaMapper ollamaMapper;

        @Override
        public AgentResponse chat(Conversation conversation) {
                OllamaChatRequest request = ollamaMapper.toOllamaChatRequest(conversation);

                OllamaChatResponse response = ollamaClient.chat(request);

                if (response == null || response.getMessage() == null) {
                        throw new RuntimeException(
                                "No response received from Ollama"
                        );
                }

                String content = response.getMessage().getContent();

                return ollamaMapper.toAgentResponse(content);
        }
}
