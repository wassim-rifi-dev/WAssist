package dev.wassim.wassist.ai.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import dev.wassim.wassist.ai.dto.request.OllamaChatRequest;
import dev.wassim.wassist.ai.dto.response.OllamaChatResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OllamaClient {
    private final RestClient ollamaRestClient;

    public OllamaChatResponse chat(OllamaChatRequest ollamaChatRequest) {
        return ollamaRestClient.post()
                .uri("/api/chat")
                .body(ollamaChatRequest)
                .retrieve()
                .body(OllamaChatResponse.class);
    }
}
