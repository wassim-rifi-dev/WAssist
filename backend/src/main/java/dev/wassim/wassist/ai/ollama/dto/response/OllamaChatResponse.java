package dev.wassim.wassist.ai.ollama.dto.response;

import dev.wassim.wassist.ai.ollama.dto.OllamaMessage;
import lombok.Getter;

@Getter
public class OllamaChatResponse {
    private String model;
    private OllamaMessage message;
    private boolean done;
}
