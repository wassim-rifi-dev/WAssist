package dev.wassim.wassist.tools;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ToolRegistry {
    private final Map<String, Tool> toolsByName;

    public ToolRegistry(List<Tool> tools) {
        this.toolsByName = tools.stream().collect(Collectors.toMap(Tool::getName, t -> t));
    }

    public Tool getTool(String name) {
        Tool tool = toolsByName.get(name);

        if (tool == null) {
            throw new IllegalArgumentException("Unknown tool: " + name);
        }
        
        return tool;
    }

    public List<Tool> getAllTools() {
        return List.copyOf(toolsByName.values());
    }
}
