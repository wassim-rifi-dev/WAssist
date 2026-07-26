package dev.wassim.wassist.domain.conversation;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.domain.dto.AgentMessage;
import dev.wassim.wassist.domain.enums.MessageRoles;

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

    public void addSystemeMessage(String content , Conversation conversation) {
        AgentMessage message = new AgentMessage(
            MessageRoles.SYSTEM,
            content
        );

        conversation.getMessages().add(message);
    }
    public void addUserMessage(String content , Conversation conversation) {
        AgentMessage message = new AgentMessage(
            MessageRoles.USER,
            content
        );

        conversation.getMessages().add(message);
    }
    public void addAssistantMessage(String content , Conversation conversation) {
        AgentMessage message = new AgentMessage(
            MessageRoles.ASSISTANT,
            content
        );

        conversation.getMessages().add(message);
    }
    public void addToolMessage(String content , Conversation conversation) {
        AgentMessage message = new AgentMessage(
            MessageRoles.TOOL,
            content
        );

        conversation.getMessages().add(message);
    }
}
