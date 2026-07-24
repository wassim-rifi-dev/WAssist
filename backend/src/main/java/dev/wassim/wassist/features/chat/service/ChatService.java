package dev.wassim.wassist.features.chat.service;

import org.springframework.stereotype.Service;

import dev.wassim.wassist.ai.ollama.OllamaService;
import dev.wassim.wassist.features.chat.dto.request.ChatRequest;
import dev.wassim.wassist.features.chat.dto.response.ChatResponse;
import dev.wassim.wassist.features.chat.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final OllamaService ollamaService;

    private final ChatMapper chatMapper;

    public ChatResponse ask(ChatRequest chatRequest) {
        String response = ollamaService.ask(chatRequest.getMessage());

        return chatMapper.toChatResponse(response);
    }
}
