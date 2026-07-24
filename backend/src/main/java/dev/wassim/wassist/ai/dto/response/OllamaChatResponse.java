package dev.wassim.wassist.ai.dto.response;

import dev.wassim.wassist.ai.dto.OllamaMessage;
import lombok.Getter;

@Getter
public class OllamaChatResponse {
    private String model;
    private OllamaMessage message;
    private boolean done;
}
