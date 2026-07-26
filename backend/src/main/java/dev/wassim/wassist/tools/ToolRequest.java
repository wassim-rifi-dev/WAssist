package dev.wassim.wassist.tools;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ToolRequest {
    private final Map<String, Object> arguments;

    public String getStringArg(String key) {
        Object value = arguments.get(key);

        if (value == null) {
            throw new IllegalArgumentException("Missing argument: " + key);
        }
        return value.toString();
    }
}
