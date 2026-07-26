package dev.wassim.wassist.agent.tools.files_tool;

import java.nio.file.Files;
import java.nio.file.Path;

import dev.wassim.wassist.agent.tools.Tool;
import dev.wassim.wassist.agent.tools.ToolRequest;
import dev.wassim.wassist.agent.tools.ToolResult;

public class ReadFileTool implements Tool {
    
    @Override
    public String getName() {
        return "read_file";
    }

    @Override
    public String getDescription() {
        return "Reads the text content of a file from the local filesystem given its path.";
    }

    @Override
    public ToolResult execute(ToolRequest request) {
        try {
            String rawPath = request.getStringArg("path");
            Path path = Path.of(rawPath).normalize();

            if (Files.notExists(path)) {
                return ToolResult.failure("File not found: " + rawPath);
            }

            String content = Files.readString(path);
            return ToolResult.success(content);
        } catch (Exception e) {
            return ToolResult.failure("Failed to read file: " + e.getMessage());
        }
    }
}
