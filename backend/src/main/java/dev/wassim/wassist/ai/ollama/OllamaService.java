package dev.wassim.wassist.ai.ollama;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import dev.wassim.wassist.ai.ollama.dto.OllamaMessage;
import dev.wassim.wassist.ai.ollama.dto.request.OllamaChatRequest;
import dev.wassim.wassist.ai.ollama.dto.response.OllamaChatResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OllamaService {
    private final RestClient ollamaRestClient;

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

        OllamaChatResponse ollamaChatResponse = ollamaRestClient
                .post()
                .uri("/api/chat")
                .body(ollamaChatRequest)
                .retrieve()
                .body(OllamaChatResponse.class);

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
