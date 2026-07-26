package dev.wassim.wassist.domain.dto.response;

import dev.wassim.wassist.domain.enums.ResponseType;
import dev.wassim.wassist.domain.toolcall.ToolCall;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AgentResponse {
    private ResponseType type;

    private String message;

    private ToolCall toolCall;
}
