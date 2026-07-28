package dev.wassim.wassist.agent.tools.files_tool;

import java.io.IOException;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.agent.tools.Tool;
import dev.wassim.wassist.agent.tools.ToolRequest;
import dev.wassim.wassist.agent.tools.ToolResult;
import dev.wassim.wassist.agent.tools.services.FileToolServices;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReadFileTool implements Tool {
    private final FileToolServices fileToolServices;

    @Override
    public String getName() {
        return "read_file";
    }

    @Override
    public String getDescription() {
        return "Reads the contents of a file from the current workspace. Use this tool only when you already know the file's path and need to examine or understand its contents. This tool does not search for files or directories; use SearchFileTool first if the file location is unknown.";
    }

    @Override
    public ToolResult execute(ToolRequest request) {
        try {
            String rawPath = request.getStringArg("path");
            String content = fileToolServices.readFile(rawPath);
            return ToolResult.success(content);
        } catch (IOException e) {
            return ToolResult.failure("Failed to read file: " + e.getMessage());
        } catch (Exception e) {
            return ToolResult.failure("Unexpected error: " + e.getMessage());
        }
    }
}
