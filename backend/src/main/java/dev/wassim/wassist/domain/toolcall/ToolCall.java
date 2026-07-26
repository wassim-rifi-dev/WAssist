package dev.wassim.wassist.domain.toolcall;

import java.util.Map;

import lombok.Data;

@Data
public class ToolCall {
    private String tool;

    private Map<String, Object> arguments;
}
