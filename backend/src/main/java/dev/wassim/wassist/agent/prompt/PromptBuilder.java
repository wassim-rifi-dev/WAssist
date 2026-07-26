package dev.wassim.wassist.agent.prompt;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.agent.tools.ToolRegistry;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PromptBuilder {
    private final ToolRegistry toolRegistry;

    public String buildSystemPrompt() {
        return """
                You are an AI Agent.

                You must always respond using JSON only.

                Allowed responses:

                MESSAGE:
                {
                    "type": "MESSAGE",
                    "message": "your answer"
                }

                TOOL_CALL:
                {
                    "type": "TOOL_CALL",
                    "toolCall": {
                        "tool": "read_file",
                        "arguments": {
                            "path": "file path"
                        }
                    }
                }

                Rules:
                - Never return normal text.
                - Never use markdown.
                - Never add explanations outside JSON.
                - Use TOOL_CALL when you need a tool.
                - Use MESSAGE when you can answer directly.

                Available tools:

                %s

                Arguments:
                {
                    "path": "string"
                }
                """.formatted(toolRegistry.formatAllTools());
    }
}
