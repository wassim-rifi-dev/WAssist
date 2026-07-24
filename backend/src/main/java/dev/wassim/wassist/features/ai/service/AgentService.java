package dev.wassim.wassist.features.ai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import dev.wassim.wassist.features.ai.client.OllamaClient;
import dev.wassim.wassist.features.ai.dto.OllamaMessage;
import dev.wassim.wassist.features.ai.dto.request.OllamaChatRequest;
import dev.wassim.wassist.features.ai.dto.response.OllamaChatResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService {
        private final RestClient ollamaRestClient;
        private final OllamaClient ollamaClient;

        @Value("${ollama.model}")
        private String model;

        public String ask(String prompt) {
                OllamaMessage ollamaMessage = new OllamaMessage(
                        "user",
                        prompt
                );

                OllamaChatRequest ollamaChatRequest = new OllamaChatRequest(
                        model,
                        List.of(ollamaMessage),
                        false
                );

                OllamaChatResponse ollamaChatResponse = ollamaClient.chat(ollamaRestClient, ollamaChatRequest);

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
