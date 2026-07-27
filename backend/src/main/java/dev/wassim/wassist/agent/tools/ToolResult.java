package dev.wassim.wassist.agent.tools;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ToolResult {
    private final boolean success;
    private final String content;
    private final String errorMessage;

    public static ToolResult success(String content) {
        return new ToolResult(true, content, null);
    }

    public static ToolResult failure(String errorMessage) {
        return new ToolResult(false, null, errorMessage);
    }

    public String toPromptText() {
        if (success) {
            return """
                    Success: true

                    Content:
                    %s
                    """.formatted(content);
        }

        return """
                Success: false

                Error:
                %s
                """.formatted(errorMessage);
    }
}