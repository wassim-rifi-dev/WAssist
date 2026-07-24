package dev.wassim.wassist.ai.ollama.dto.request;

import java.util.List;

import dev.wassim.wassist.ai.ollama.dto.OllamaMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OllamaChatRequest {
    private String model;
    private List<OllamaMessage> messages;
    private boolean stream;
}
