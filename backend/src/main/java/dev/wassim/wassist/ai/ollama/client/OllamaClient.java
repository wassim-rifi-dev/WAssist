package dev.wassim.wassist.ai.ollama.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import dev.wassim.wassist.ai.ollama.dto.request.OllamaChatRequest;
import dev.wassim.wassist.ai.ollama.dto.response.OllamaChatResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OllamaClient {
    private final RestClient ollamaRestClient;

    public OllamaChatResponse chat(OllamaChatRequest request) {
        return ollamaRestClient.post()
                .uri("/api/chat")
                .body(request)
                .retrieve()
                .body(OllamaChatResponse.class);
    }
}
