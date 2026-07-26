package dev.wassim.wassist.domain.conversation;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.domain.dto.AgentMessage;

@Component
public class ConversationManager {
    private Conversation conversation;

    public Conversation createConversation() {
        Conversation conversation = new Conversation();
        return conversation;
    }

    public Conversation getCurrentConversation() {
        return conversation;
    }

    public void addMessage(AgentMessage message , Conversation conversation) {
        conversation.getMessages().add(message);
    }
}
