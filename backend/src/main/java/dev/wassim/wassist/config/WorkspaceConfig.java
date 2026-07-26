package dev.wassim.wassist.config;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "agent.workspace")
@Data
public class WorkspaceConfig {
    private Path path;
}
