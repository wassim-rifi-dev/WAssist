package dev.wassim.wassist.agent;

import java.util.Map;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.agent.tools.ToolExecutor;
import dev.wassim.wassist.agent.tools.ToolResult;
import dev.wassim.wassist.ai.provider.AIProvider;
import dev.wassim.wassist.common.exceptions.AgentExecutionException;
import dev.wassim.wassist.domain.conversation.Conversation;
import dev.wassim.wassist.domain.conversation.ConversationManager;
import dev.wassim.wassist.domain.dto.response.AgentResponse;
import dev.wassim.wassist.domain.enums.ResponseType;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AgentReasoningEngine {
    private final AIProvider aiProvider;
    private final ToolExecutor toolExecutor;
    private final ConversationManager conversationManager;

    private static final int MAX_ITERATIONS = 5;

    public String run(Conversation conversation) {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            AgentResponse response = aiProvider.chat(conversation);

            switch (response.getType()) {

                case ResponseType.MESSAGE:
                    conversationManager.addAssistantMessage(response.getMessage(), conversation);
                    return response.getMessage();

                case ResponseType.TOOL_CALL:
                    conversationManager.addAssistantToolCall(response.getToolCall() , conversation);

                    String toolName = response
                        .getToolCall()
                        .getTool();

                    Map<String , Object> argument = response.getToolCall().getArguments();

                    ToolResult result = toolExecutor.execute(toolName , argument);

                    conversationManager.addToolMessage(toolName , result.toPromptText(), conversation);

                default:
                    break;
            }
        }

        throw new AgentExecutionException(
            "Agent reached maximum iterations"
        );
    }
}
