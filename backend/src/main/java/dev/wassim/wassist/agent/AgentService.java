package dev.wassim.wassist.agent;

import org.springframework.stereotype.Service;

import dev.wassim.wassist.agent.tools.Tool;
import dev.wassim.wassist.agent.tools.ToolRegistry;
import dev.wassim.wassist.agent.tools.ToolRequest;
import dev.wassim.wassist.agent.tools.ToolResult;
import dev.wassim.wassist.ai.provider.AIProvider;
import dev.wassim.wassist.domain.dto.response.AgentResponse;
import dev.wassim.wassist.domain.enums.ResponseType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService {
        private final AIProvider aiProvider;
        private final ToolRegistry toolRegistry;

        private static final int MAX_ITERATIONS = 5;

        public String ask(String prompt) {

                String currentPrompt = prompt;


                for (int i = 0; i < MAX_ITERATIONS; i++) {

                        AgentResponse response = aiProvider.chat(currentPrompt);

                        if (response.getType() == ResponseType.MESSAGE) {

                                return response.getMessage();
                        }

                        if (response.getType() == ResponseType.TOOL_CALL) {


                                String toolName = response
                                        .getToolCall()
                                        .getTool();


                                Tool tool = toolRegistry.getToolByName(toolName);


                                ToolRequest request = new ToolRequest(
                                        response.getToolCall().getArguments()
                                );


                                ToolResult result = tool.execute(request);

                                currentPrompt = """
                                        A tool was executed.

                                        Tool:
                                        %s

                                        Result:
                                        %s

                                        Continue and provide the final answer using JSON only.
                                        """.formatted(
                                                toolName,
                                                result.toPromptText()
                                        );
                        }

                }

                throw new RuntimeException(
                        "Agent reached maximum iterations"
                );
        }
}