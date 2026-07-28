package dev.wassim.wassist.agent.tools.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import dev.wassim.wassist.agent.tools.ToolResult;
import dev.wassim.wassist.common.exceptions.NotAFileException;
import dev.wassim.wassist.common.exceptions.PathOutsideWorkspaceException;
import dev.wassim.wassist.config.WorkspaceConfig;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileToolServices {
    private final WorkspaceConfig workspaceConfig;

    private String serachFileResponse(List<Path> results) {
        return """
                Found %d match(es):

                %s
                """.formatted(
                    results.size(),
                    results.stream()
                            .map(Path::toString)
                            .collect(Collectors.joining("\n"))
                );
    }

    public Path getWorkspace() {
        Path workspace = workspaceConfig.getPath().toAbsolutePath().normalize();

        return workspace;
    }

    private Path resolve(String relativePath) {
        Path workspace = getWorkspace();

        Path target = workspace.resolve(relativePath).normalize();

        if (!target.startsWith(workspace)) {
            throw new PathOutsideWorkspaceException("Access denied");
        }

        return target;
    }

    private boolean exists(Path path) {
        return Files.exists(path);
    }

    private boolean isFile(Path path) {
        return Files.isRegularFile(path);
    }

    private boolean isEgnored(Path path) {
        return workspaceConfig.getIgnoredDirectories().contains(path.getFileName().toString());
    }

    public String readFile(String relativePath) throws IOException {
        Path path = resolve(relativePath);

        if (!exists(path)) {
            throw new FileNotFoundException("File does not exist: " + path);
        }

        if (!isFile(path)) {
            throw new NotAFileException("Path is not a file (it's a directory): " + path);
        }

        return Files.readString(path);
    }

    public ToolResult searchFile(String target) {
        try (Stream<Path> paths = Files.walk(getWorkspace())) {
            List<Path> results = paths
                                .filter(path -> !isEgnored(path))
                                .filter(path -> path.getFileName().toString().equalsIgnoreCase(target))
                                .collect(Collectors.toList());

            if (results.isEmpty()) {
                return ToolResult.failure("No file or folder found with this name.");
            }

            String response = serachFileResponse(results);

            return ToolResult.success(response);
        } catch (IOException e) {
            return ToolResult.failure("Error while searching files: " + e.getMessage());
        }
    }
}
