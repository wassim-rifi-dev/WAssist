package dev.wassim.wassist.agent;

import org.springframework.stereotype.Service;

import dev.wassim.wassist.agent.tools.Tool;
import dev.wassim.wassist.agent.tools.ToolRegistry;
import dev.wassim.wassist.agent.tools.ToolRequest;
import dev.wassim.wassist.agent.tools.ToolResult;
import dev.wassim.wassist.ai.provider.AIProvider;
import dev.wassim.wassist.domain.dto.response.AgentResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgentService {
        private final AIProvider aiProvider;
        private final ToolRegistry toolRegistry;

        public ToolResult executeTool(String toolName, ToolRequest request) {
                Tool tool = toolRegistry.getToolByName(toolName);

                return tool.execute(request);
        }

        public AgentResponse ask(String prompt) {
                return aiProvider.chat(prompt);
        }
}
