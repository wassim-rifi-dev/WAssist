package dev.wassim.wassist.agent;

import org.springframework.stereotype.Service;

import dev.wassim.wassist.ai.provider.AIProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService {
        private final AIProvider aiProvider;

        public String ask(String prompt) {
                return aiProvider.chat(prompt);
        }
}
