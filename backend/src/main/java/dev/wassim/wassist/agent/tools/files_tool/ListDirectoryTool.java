package dev.wassim.wassist.agent.tools.files_tool;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.agent.tools.Tool;
import dev.wassim.wassist.agent.tools.ToolRequest;
import dev.wassim.wassist.agent.tools.ToolResult;
import dev.wassim.wassist.agent.tools.services.FileToolServices;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ListDirectoryTool implements Tool {
    private final FileToolServices fileToolServices;

    @Override
    public String getName() {
        return "list_directory";
    }

    @Override
    public String getDescription() {
        return "Lists the files and subdirectories located in a specified directory within the current workspace. This tool only provides the directory structure and does not read file contents, analyze projects, or perform searches.";
    }

    @Override
    public ToolResult execute(ToolRequest request) {
        try {
            String target = request.getStringArg("path");

            return fileToolServices.listDirectory(target);
        } catch (Exception e) {
            return ToolResult.failure("Unexpected error: " + e.getMessage());
        }
    }
}
