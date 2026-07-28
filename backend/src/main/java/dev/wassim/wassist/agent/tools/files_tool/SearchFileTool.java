package dev.wassim.wassist.agent.tools.files_tool;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.agent.tools.Tool;
import dev.wassim.wassist.agent.tools.ToolRequest;
import dev.wassim.wassist.agent.tools.ToolResult;
import dev.wassim.wassist.agent.tools.services.FileToolServices;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SearchFileTool implements Tool {
    private final FileToolServices fileToolServices;

    @Override
    public String getName() {
        return "search_file";
    }

    @Override
    public String getDescription() {
        return "Searches the current workspace recursively for files or directories whose names match the requested query. Use this tool when you know the name (or part of the name) of a file, folder, or project but do not know its location. The tool returns the matching paths only; it does not read or analyze file contents.";
    }

    @Override
    public ToolResult execute(ToolRequest request) {
        try {
            String target = request.getStringArg("target");

            return fileToolServices.searchFile(target);
        } catch (Exception e) {
            return ToolResult.failure("Unexpected error: " + e.getMessage());
        }
    }
}
