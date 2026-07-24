package dev.wassim.wassist.ai.service;

import org.springframework.stereotype.Service;

import dev.wassim.wassist.ai.client.OllamaClient;
import dev.wassim.wassist.ai.dto.OllamaMessage;
import dev.wassim.wassist.ai.dto.request.OllamaChatRequest;
import dev.wassim.wassist.ai.dto.response.OllamaChatResponse;
import dev.wassim.wassist.ai.mapper.OllamaMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService {
        private final OllamaClient ollamaClient;
        private final OllamaMapper ollamaMapper;

        public String ask(String prompt) {
                OllamaMessage ollamaMessage = ollamaMapper.tOllamaMessage(prompt);

                OllamaChatRequest ollamaChatRequest = ollamaMapper.tOllamaChatRequest(ollamaMessage);

                OllamaChatResponse ollamaChatResponse = ollamaClient.chat(ollamaChatRequest);

                if (ollamaChatResponse == null || ollamaChatResponse.getMessage() == null) {
                        throw new RuntimeException(
                                "No response received from Ollama"
                        );
                }

                return ollamaChatResponse
                        .getMessage()
                        .getContent();
        }
}
