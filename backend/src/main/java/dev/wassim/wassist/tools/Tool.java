package dev.wassim.wassist.tools;

public interface Tool {
    String getName();

    String getDescription();

    ToolRequest execute(ToolRequest request);
}
