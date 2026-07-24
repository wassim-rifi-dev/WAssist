package dev.wassim.wassist.ai.ollama.dto.request;

import java.util.List;

import dev.wassim.wassist.ai.ollama.dto.OllamaMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OllamaChatRequest {
    private String model;
    private List<OllamaMessage> messages;
    private boolean stream;
}
