package dev.wassim.wassist.agent.tools;

public interface Tool {
    String getName();

    String getDescription();

    ToolRequest execute(ToolRequest request);
}
