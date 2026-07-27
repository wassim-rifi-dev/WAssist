package dev.wassim.wassist.domain.conversation;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.domain.dto.AgentMessage;
import dev.wassim.wassist.domain.enums.MessageRoles;
import dev.wassim.wassist.domain.toolcall.ToolCall;

@Component
public class ConversationManager {
    public Conversation createConversation() {
        return new Conversation();
    }

    public void addSystemMessage(String content, Conversation conversation) {
        AgentMessage message = new AgentMessage(
                MessageRoles.SYSTEM,
                content,
                null,
                null
        );

        conversation.getMessages().add(message);
    }

    public void addUserMessage(String content, Conversation conversation) {
        AgentMessage message = new AgentMessage(
                MessageRoles.USER,
                content,
                null,
                null
        );

        conversation.getMessages().add(message);
    }

    public void addAssistantMessage(String content, Conversation conversation) {
        AgentMessage message = new AgentMessage(
                MessageRoles.ASSISTANT,
                content,
                null,
                null
        );

        conversation.getMessages().add(message);
    }

    public void addAssistantToolCall(ToolCall toolCall, Conversation conversation) {
        AgentMessage message = new AgentMessage(
                MessageRoles.ASSISTANT,
                null,
                toolCall,
                null
        );

        conversation.getMessages().add(message);
    }

    public void addToolMessage(String toolName, String content, Conversation conversation) {
        AgentMessage message = new AgentMessage(
                MessageRoles.TOOL,
                content,
                null,
                toolName
        );

        conversation.getMessages().add(message);
    }
}
