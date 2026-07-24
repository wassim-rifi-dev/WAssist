package dev.wassim.wassist.ai.qwen.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import dev.wassim.wassist.ai.qwen.dto.QwenMessage;
import dev.wassim.wassist.ai.qwen.dto.request.QwenChatRequest;

@Component
public class QwenMapper {
    @Value("${ollama.model}")
    private String model;

    public QwenMessage toQwenMessage(String prompt) {
        return new QwenMessage(
            "user",
            prompt
        );
    }

    public QwenChatRequest toQwenChatRequest(QwenMessage qwenMessage) {
        return new QwenChatRequest(
            model, 
            List.of(qwenMessage), 
            false);
    }
}
