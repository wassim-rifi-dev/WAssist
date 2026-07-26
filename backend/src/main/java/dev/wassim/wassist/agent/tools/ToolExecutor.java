package dev.wassim.wassist.agent.tools;

import java.util.Map;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.common.exceptions.ToolNotFound;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ToolExecutor {
    private final ToolRegistry toolRegistry;

    public ToolResult execute(String toolName , Map<String , Object> argument) {
        Tool tool = toolRegistry.getToolByName(toolName);

        if (tool == null) {
            throw new ToolNotFound("Unknown tool: " + toolName);
        }

        ToolRequest request = new ToolRequest(argument);

        ToolResult result = tool.execute(request);

        return result;
    }
}
