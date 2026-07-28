package dev.wassim.wassist.agent.tools.files_tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            Path workspace = fileToolServices.getWorkspace();

            try (Stream<Path> paths = Files.walk(workspace)) {
                List<Path> results = paths.filter(path -> path.getFileName().toString().equalsIgnoreCase(target)).collect(Collectors.toList());

                if (results.isEmpty()) {
                    return ToolResult.failure("No file or folder found with this name.");
                }

                String response = """
                    Found %d match(es):

                    %s
                    """.formatted(
                        results.size(),
                        results.stream()
                                .map(Path::toString)
                                .collect(Collectors.joining("\n"))
                    );

                return ToolResult.success(response);
            } catch (IOException e) {
                return ToolResult.failure("Error while searching files: " + e.getMessage());
            }
        } catch (Exception e) {
            return ToolResult.failure("Unexpected error: " + e.getMessage());
        }
    }
}
