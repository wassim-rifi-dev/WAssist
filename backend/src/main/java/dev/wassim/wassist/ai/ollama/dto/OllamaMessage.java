package dev.wassim.wassist.ai.ollama.dto;

import dev.wassim.wassist.domain.toolcall.ToolCall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OllamaMessage {
        private String role;
        private String content;
        private ToolCall toolCall;
        private String toolName;
}
