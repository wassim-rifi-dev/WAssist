package dev.wassim.wassist.agent;

import org.springframework.stereotype.Service;

import dev.wassim.wassist.domain.conversation.Conversation;
import dev.wassim.wassist.domain.conversation.ConversationManager;
import dev.wassim.wassist.domain.dto.AgentMessage;
import dev.wassim.wassist.domain.enums.MessageRoles;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService {
        private final ConversationManager conversationManager;
        private final AgentReasoningEngine agentReasoningEngine;

        private static final String SYSTEM_PROMPT = """
                You are an AI Agent.

                You must always respond using JSON only.

                Allowed responses:

                MESSAGE:
                {
                        "type": "MESSAGE",
                        "message": "your answer"
                }

                TOOL_CALL:
                {
                        "type": "TOOL_CALL",
                        "toolCall": {
                        "tool": "read_file",
                        "arguments": {
                                "path": "file path"
                        }
                        }
                }

                Rules:
                - Never return normal text.
                - Never use markdown.
                - Never add explanations outside JSON.
                - Use TOOL_CALL when you need a tool.
                - Use MESSAGE when you can answer directly.


                Available tools:

                read_file:
                Reads a text file from the workspace.

                Arguments:
                {
                        "path": "string"
                }
                """;

        public String ask(String prompt) {
                Conversation conversation = conversationManager.createConversation();

                AgentMessage systemMessage = new AgentMessage(MessageRoles.SYSTEM, SYSTEM_PROMPT);
                conversationManager.addMessage(systemMessage, conversation);

                AgentMessage userMessage = new AgentMessage(MessageRoles.USER , prompt);
                conversationManager.addMessage(userMessage , conversation);

                agentReasoningEngine.run(conversation);

                throw new RuntimeException(
                        "Agent reached maximum iterations"
                );
        }
}