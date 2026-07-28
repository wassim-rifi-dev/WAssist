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

    private String searchFileResponse(List<Path> results) {
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

    private String listDirectoryResponse(Path directory, List<Path> results) {
        String items = results.stream()
                .map(path -> "- %s (%s)".formatted(
                        path.getFileName(),
                        Files.isDirectory(path) ? "Directory" : "File"))
                .collect(Collectors.joining("\n"));

        return """
                Directory:
                %s

                Items:
                %s
                """.formatted(
                directory,
                items
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

    public ToolResult searchFile(String query) {
        try (Stream<Path> paths = Files.walk(getWorkspace())) {
            List<Path> results = paths
                                .filter(path -> !isEgnored(path))
                                .filter(path -> path.getFileName().toString().equalsIgnoreCase(query))
                                .map(path -> getWorkspace().relativize(path))
                                .collect(Collectors.toList());

            if (results.isEmpty()) {
                return ToolResult.failure("No file or folder found with this name.");
            }

            String response = searchFileResponse(results);

            return ToolResult.success(response);
        } catch (IOException e) {
            return ToolResult.failure("Error while searching files: " + e.getMessage());
        }
    }

    public ToolResult listDirectory(String relativePath) throws IOException {
        Path path = resolve(relativePath);

        if (!exists(path)) {
            throw new FileNotFoundException("File does not exist: " + path);
        }

        if (isFile(path)) {
            throw new NotAFileException("Path is not a directory: " + path);
        }

        try (Stream<Path> directories = Files.list(path)) {
            List<Path> results = directories
                                    .filter(directory -> !isEgnored(directory))
                                    .collect(Collectors.toList());

            if (results.isEmpty()) {
                return ToolResult.failure("No file or folder found in this folder");
            }

            String response = listDirectoryResponse(path, results);

            return ToolResult.success(response);
        }
    }
}
