package dev.wassim.wassist.agent.tools;

public interface Tool {
    String getName();

    String getDescription();

    ToolResult execute(ToolRequest request);
}
