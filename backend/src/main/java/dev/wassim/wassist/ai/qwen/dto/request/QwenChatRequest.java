package dev.wassim.wassist.ai.qwen.dto.request;

import java.util.List;

import dev.wassim.wassist.ai.qwen.dto.QwenMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QwenChatRequest {
    private String model;
    private List<QwenMessage> messages;
    private boolean stream;
}
