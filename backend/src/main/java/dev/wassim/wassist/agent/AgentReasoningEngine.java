package dev.wassim.wassist.agent;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.agent.tools.Tool;
import dev.wassim.wassist.agent.tools.ToolRegistry;
import dev.wassim.wassist.agent.tools.ToolRequest;
import dev.wassim.wassist.agent.tools.ToolResult;
import dev.wassim.wassist.ai.provider.AIProvider;
import dev.wassim.wassist.common.exceptions.AgentExecutionException;
import dev.wassim.wassist.domain.conversation.Conversation;
import dev.wassim.wassist.domain.conversation.ConversationManager;
import dev.wassim.wassist.domain.dto.AgentMessage;
import dev.wassim.wassist.domain.dto.response.AgentResponse;
import dev.wassim.wassist.domain.enums.MessageRoles;
import dev.wassim.wassist.domain.enums.ResponseType;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AgentReasoningEngine {
    private final AIProvider aiProvider;
    private final ToolRegistry toolRegistry;
    private final ConversationManager conversationManager;
    
    private static final int MAX_ITERATIONS = 5;

    public String run(Conversation conversation) {
        for (int i = 0; i < MAX_ITERATIONS; i++) {

            AgentResponse response = aiProvider.chat(conversation);

            if (response.getType() == ResponseType.MESSAGE) {
                    AgentMessage assistantMessage = new AgentMessage(MessageRoles.ASSISTANT , response.getMessage());
                    conversationManager.addMessage(assistantMessage , conversation);

                    return response.getMessage();
            }

            if (response.getType() == ResponseType.TOOL_CALL) {
                    AgentMessage assistantMessage = new AgentMessage(MessageRoles.ASSISTANT , "Calling tool: " + response.getToolCall().getTool());
                    conversationManager.addMessage(assistantMessage , conversation);

                    String toolName = response
                            .getToolCall()
                            .getTool();

                    Tool tool = toolRegistry.getToolByName(toolName);


                    ToolRequest request = new ToolRequest(
                            response.getToolCall().getArguments()
                    );


                    ToolResult result = tool.execute(request);

                    AgentMessage toolMessage = new AgentMessage(MessageRoles.TOOL, result.toPromptText());
                    conversationManager.addMessage(toolMessage , conversation);
            }

        }
        throw new AgentExecutionException(
            "Agent reached maximum iterations"
        );
    }
}
