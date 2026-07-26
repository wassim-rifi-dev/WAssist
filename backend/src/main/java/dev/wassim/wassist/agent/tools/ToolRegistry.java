package dev.wassim.wassist.agent.tools;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.common.exceptions.ToolNotFound;

@Component
public class ToolRegistry {
    private final Map<String, Tool> tools;

    private String formatAvailableTool(Tool tool) {
        return tool.getName() + ":\n"
                + tool.getDescription() + ".\n\n";
    }

    public ToolRegistry(List<Tool> t) {
        this.tools = t.stream().collect(Collectors.toMap(Tool::getName, tool -> tool));
    }

    public Tool getToolByName(String name) {
        Tool tool = tools.get(name);

        if (tool == null) {
            throw new ToolNotFound("Unknown tool: " + name);
        }

        return tool;
    }

    public String formatAllTools() {
        List<Tool> tools = getAllTools();

        return String.join("", tools.stream().map(this::formatAvailableTool).toList());
    }

    public List<Tool> getAllTools() {
        return List.copyOf(tools.values());
    }
}
