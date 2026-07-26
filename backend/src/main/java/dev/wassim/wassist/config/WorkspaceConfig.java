package dev.wassim.wassist.config;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "agent.workspace")
public class WorkspaceConfig {
    private final Path path;
}
