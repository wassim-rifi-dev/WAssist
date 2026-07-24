package dev.wassim.wassist.ai.qwen.dto.response;

import dev.wassim.wassist.ai.qwen.dto.QwenMessage;
import lombok.Getter;

@Getter
public class QwenChatResponse {
    private String model;
    private QwenMessage message;
    private boolean done;
}
