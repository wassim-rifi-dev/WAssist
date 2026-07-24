package dev.wassim.wassist.ai.ollama.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.wassim.wassist.ai.ollama.dto.OllamaMessage;
import dev.wassim.wassist.ai.ollama.dto.request.OllamaChatRequest;

@Component
public class OllamaMapper {
    @Value("${ollama.model}")
    private String model;

    public OllamaMessage toOllamaMessage(String prompt) {
        return new OllamaMessage(
            "user",
            prompt
        );
    }

    public OllamaChatRequest toOllamaChatRequest(OllamaMessage message) {
        return new OllamaChatRequest(
            model, 
            List.of(message), 
            false);
    }
}
