package dev.wassim.wassist.ai.ollama.dto.response;

import dev.wassim.wassist.ai.ollama.dto.OllamaMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OllamaChatRespense {
    private String model;
    private OllamaMessage messages;
    private boolean done;
}
