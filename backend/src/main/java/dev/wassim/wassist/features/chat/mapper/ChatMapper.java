package dev.wassim.wassist.features.chat.mapper;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.features.chat.dto.response.ChatResponse;

@Component
public class ChatMapper {
    public ChatResponse toChatResponse(String message) {
        return new ChatResponse(
            message
        );
    }
}
