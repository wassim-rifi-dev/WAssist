package dev.wassim.wassist.ai.ollama.provider;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.ai.provider.AIProvider;
import dev.wassim.wassist.ai.ollama.client.OllamaClient;
import dev.wassim.wassist.ai.ollama.dto.OllamaMessage;
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
        public String chat(String prompt) {
                OllamaMessage message = ollamaMapper.toOllamaMessage(prompt);

                OllamaChatRequest request = ollamaMapper.toOllamaChatRequest(message);

                OllamaChatResponse response = ollamaClient.chat(request);

                if (response == null || response.getMessage() == null) {
                        throw new RuntimeException(
                                "No response received from Ollama"
                        );
                }

                return response
                        .getMessage()
                        .getContent();
        }
}
