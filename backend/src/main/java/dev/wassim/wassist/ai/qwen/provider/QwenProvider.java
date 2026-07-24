package dev.wassim.wassist.ai.qwen.provider;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.ai.provider.AIProvider;
import dev.wassim.wassist.ai.qwen.client.QwenClient;
import dev.wassim.wassist.ai.qwen.dto.QwenMessage;
import dev.wassim.wassist.ai.qwen.dto.request.QwenChatRequest;
import dev.wassim.wassist.ai.qwen.dto.response.QwenChatResponse;
import dev.wassim.wassist.ai.qwen.mapper.QwenMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QwenProvider implements AIProvider {
        private final QwenClient qwenClient;
        private final QwenMapper qwenMapper;

        @Override
        public String chat(String prompt) {
                QwenMessage qwenMessage = qwenMapper.toQwenMessage(prompt);

                QwenChatRequest qwenChatRequest = qwenMapper.toQwenChatRequest(qwenMessage);

                QwenChatResponse qwenChatResponse = qwenClient.chat(qwenChatRequest);

                if (qwenChatResponse == null || qwenChatResponse.getMessage() == null) {
                        throw new RuntimeException(
                                "No response received from Ollama"
                        );
                }

                return qwenChatResponse
                        .getMessage()
                        .getContent();
        }
}
