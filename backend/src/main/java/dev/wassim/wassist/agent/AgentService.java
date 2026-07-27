package dev.wassim.wassist.agent;

import org.springframework.stereotype.Service;

import dev.wassim.wassist.agent.prompt.PromptBuilder;
import dev.wassim.wassist.domain.conversation.Conversation;
import dev.wassim.wassist.domain.conversation.ConversationManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService {
        private final ConversationManager conversationManager;
        private final AgentReasoningEngine agentReasoningEngine;
        private final PromptBuilder promptBuilder;

        public String ask(String prompt) {
                Conversation conversation = conversationManager.createConversation();

                conversationManager.addSystemMessage(promptBuilder.buildSystemPrompt(), conversation);

                conversationManager.addUserMessage(prompt, conversation);

                return agentReasoningEngine.run(conversation);
        }
}