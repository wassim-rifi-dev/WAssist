package dev.wassim.wassist.ai.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.wassim.wassist.ai.dto.OllamaMessage;
import dev.wassim.wassist.ai.dto.request.OllamaChatRequest;

@Component
public class OllamaMapper {
    @Value("${ollama.model}")
    private String model;

    public OllamaMessage tOllamaMessage(String prompt) {
        return new OllamaMessage(
            "user",
            prompt
        );
    }

    public OllamaChatRequest tOllamaChatRequest(OllamaMessage ollamaMessage) {
        return new OllamaChatRequest(
            model, 
            List.of(ollamaMessage), 
            false);
    }
}
