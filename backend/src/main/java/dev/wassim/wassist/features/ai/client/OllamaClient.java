package dev.wassim.wassist.features.ai.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import dev.wassim.wassist.features.ai.dto.request.OllamaChatRequest;
import dev.wassim.wassist.features.ai.dto.response.OllamaChatResponse;

@Component
public class OllamaClient {
    public OllamaChatResponse chat(RestClient ollamaRestClient , OllamaChatRequest ollamaChatRequest) {
        return ollamaRestClient.post()
                .uri("/api/chat")
                .body(ollamaChatRequest)
                .retrieve()
                .body(OllamaChatResponse.class);
    }
}
