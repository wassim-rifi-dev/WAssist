package dev.wassim.wassist.agent.tools.files_tool;

import java.io.IOException;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.agent.tools.Tool;
import dev.wassim.wassist.agent.tools.ToolRequest;
import dev.wassim.wassist.agent.tools.ToolResult;
import dev.wassim.wassist.agent.workspace.WorkspaceService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReadFileTool implements Tool {
    private final WorkspaceService workspaceService;

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
            String content = workspaceService.readFile(rawPath);
            return ToolResult.success(content);
        } catch (IOException e) {
            return ToolResult.failure("Failed to read file: " + e.getMessage());
        } catch (Exception e) {
            return ToolResult.failure("Unexpected error: " + e.getMessage());
        }
    }
}
