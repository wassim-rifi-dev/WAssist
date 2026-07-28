package dev.wassim.wassist.config;

import java.nio.file.Path;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "agent.workspace")
public class WorkspaceConfig {
    private Path path;

    private List<String> ignoredDirectories;
}
