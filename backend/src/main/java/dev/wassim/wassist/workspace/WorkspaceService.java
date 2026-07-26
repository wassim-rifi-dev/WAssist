package dev.wassim.wassist.workspace;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import dev.wassim.wassist.config.WorkspaceConfig;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceConfig workspaceConfig;
    
    public Path resolve(String relativePath) {
        Path workspace = workspaceConfig.getPath().toAbsolutePath().normalize();

        Path target = workspace.resolve(relativePath).normalize();

        if (!target.startsWith(workspace)) {
            throw new SecurityException("Access denied");
        }

        return target;
    }

    public boolean exists(Path file) {
        return Files.exists(file);
    }

    public String readFile(String relativePath) {
        try {
            Path file = resolve(relativePath);

            if (!exists(file)) {
                throw new FileNotFoundException("File not found");
            }

            return Files.readString(file);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file", e);
        }
    }
}
