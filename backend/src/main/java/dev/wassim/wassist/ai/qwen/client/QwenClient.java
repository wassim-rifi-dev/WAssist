package dev.wassim.wassist.ai.qwen.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import dev.wassim.wassist.ai.qwen.dto.request.QwenChatRequest;
import dev.wassim.wassist.ai.qwen.dto.response.QwenChatResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QwenClient {
    private final RestClient qwenRestClient;

    public QwenChatResponse chat(QwenChatRequest qwenChatRequest) {
        return qwenRestClient.post()
                .uri("/api/chat")
                .body(qwenChatRequest)
                .retrieve()
                .body(QwenChatResponse.class);
    }
}
