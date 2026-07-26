package dev.wassim.wassist.workspace;

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

    public boolean existe(Path file) {
        return Files.exists(file);
    }
}
