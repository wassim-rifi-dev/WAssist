package dev.wassim.wassist.features.chat.service;

import org.springframework.stereotype.Service;

import dev.wassim.wassist.agent.AgentService;
import dev.wassim.wassist.domain.dto.response.AgentResponse;
import dev.wassim.wassist.features.chat.dto.request.ChatRequest;
import dev.wassim.wassist.features.chat.dto.response.ChatResponse;
import dev.wassim.wassist.features.chat.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final AgentService agentService;

    private final ChatMapper chatMapper;

    public ChatResponse ask(ChatRequest chatRequest) {
        AgentResponse response = agentService.ask(chatRequest.getMessage());

        return chatMapper.toChatResponse(response.getMessage());
    }
}
