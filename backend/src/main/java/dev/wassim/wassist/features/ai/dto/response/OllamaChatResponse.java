package dev.wassim.wassist.features.ai.dto.response;

import dev.wassim.wassist.features.ai.dto.OllamaMessage;
import lombok.Getter;

@Getter
public class OllamaChatResponse {
    private String model;
    private OllamaMessage message;
    private boolean done;
}
