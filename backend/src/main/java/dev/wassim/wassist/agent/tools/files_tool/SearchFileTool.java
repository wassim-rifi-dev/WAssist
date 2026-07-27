package dev.wassim.wassist.agent.tools.files_tool;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.agent.tools.Tool;
import dev.wassim.wassist.agent.tools.services.FileToolServices;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SearchFileTool implements Tool {
    private final FileToolServices fileToolServices;
}
