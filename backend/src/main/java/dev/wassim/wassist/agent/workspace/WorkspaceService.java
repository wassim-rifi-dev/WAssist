package dev.wassim.wassist.agent.workspace;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import dev.wassim.wassist.common.exceptions.NotAFileException;
import dev.wassim.wassist.config.WorkspaceConfig;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceConfig workspaceConfig;

    public Path getWorkspace() {
        Path workspace = workspaceConfig.getPath().toAbsolutePath().normalize();

        return workspace;
    }

    public Path resolve(String relativePath) {
        Path workspace = workspaceConfig.getPath().toAbsolutePath().normalize();

        Path target = workspace.resolve(relativePath).normalize();

        if (!target.startsWith(workspace)) {
            throw new SecurityException("Access denied");
        }

        return target;
    }

    public boolean exists(Path path) {
        return Files.exists(path);
    }

    public boolean isFile(Path path) {
        return Files.isRegularFile(path);
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
}
