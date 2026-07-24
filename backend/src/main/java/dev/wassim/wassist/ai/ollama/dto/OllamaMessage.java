package dev.wassim.wassist.ai.ollama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OllamaMessage {
        private String role;
        private String content;
}
