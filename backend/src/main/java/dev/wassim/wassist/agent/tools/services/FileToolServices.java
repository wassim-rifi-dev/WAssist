package dev.wassim.wassist.agent.tools.services;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
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

    private Path getWorkspace() throws IOException {
        return workspaceConfig.getPath()
                .toAbsolutePath()
                .toRealPath();
    }

    private Path resolve(String relativePath) throws IOException {
        return getWorkspace()
                .resolve(relativePath)
                .normalize();
    }

    private Path validateExistingPath(String relativePath) throws IOException {
        Path workspace = getWorkspace();

        Path target = resolve(relativePath);

        Path realTarget = target.toRealPath();

        if (!realTarget.startsWith(workspace)) {
            throw new PathOutsideWorkspaceException("Access denied.");
        }

        return realTarget;
    }

    private boolean isIgnored(Path path) {
        return workspaceConfig.getIgnoredDirectories()
                .stream()
                .anyMatch(name -> name.equalsIgnoreCase(path.getFileName().toString()));
    }

    public String readFile(String relativePath) throws IOException {
        Path file = validateExistingPath(relativePath);

        if (!Files.isRegularFile(file)) {
            throw new NotAFileException("Path is not a file: " + relativePath);
        }

        return Files.readString(file);
    }

    public ToolResult searchFile(String query) {
        List<Path> results = new ArrayList<>();

        try {
            Path workspace = getWorkspace();

            Files.walkFileTree(workspace, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (!dir.equals(workspace) && isIgnored(dir)) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }

                    String name = dir.getFileName().toString().toLowerCase();

                    if (name.contains(query.toLowerCase())) {
                        results.add(workspace.relativize(dir));
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    String name = file.getFileName().toString().toLowerCase();

                    if (name.contains(query.toLowerCase())) {
                        results.add(workspace.relativize(file));
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }

            });

            results.sort(Path::compareTo);

            if (results.isEmpty()) {
                return ToolResult.failure("No file or folder found.");
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
            return ToolResult.failure("Search failed: " + e.getMessage());
        }
    }

    public ToolResult listDirectory(String relativePath) throws IOException {
        Path directory = validateExistingPath(relativePath);

        if (!Files.isDirectory(directory)) {
            throw new NotAFileException("Path is not a directory: " + relativePath);
        }

        Path workspace = getWorkspace();

        try (Stream<Path> stream = Files.list(directory)) {
            List<Path> items = stream
                    .filter(path -> !isIgnored(path))
                    .sorted()
                    .toList();

            String response = """
                    Directory:
                    %s

                    Items:
                    %s
                    """.formatted(
                    workspace.relativize(directory),
                    items.isEmpty()
                            ? "(empty)"
                            : items.stream()
                                    .map(path -> "- %s (%s)".formatted(
                                            path.getFileName(),
                                            Files.isDirectory(path)
                                                    ? "Directory"
                                                    : "File"))
                                    .collect(Collectors.joining("\n"))
            );

            return ToolResult.success(response);
        }
    }
}
