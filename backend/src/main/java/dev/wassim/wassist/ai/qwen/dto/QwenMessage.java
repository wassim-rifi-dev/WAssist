package dev.wassim.wassist.ai.qwen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QwenMessage {
        private String role;
        private String content;
}
