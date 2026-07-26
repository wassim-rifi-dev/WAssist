package dev.wassim.wassist.ai.provider;

import dev.wassim.wassist.domain.conversation.Conversation;
import dev.wassim.wassist.domain.dto.response.AgentResponse;

public interface AIProvider {
    AgentResponse chat(Conversation conversation);
}
