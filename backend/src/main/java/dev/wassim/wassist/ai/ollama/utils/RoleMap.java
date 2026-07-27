package dev.wassim.wassist.ai.ollama.utils;

import org.springframework.stereotype.Component;

import dev.wassim.wassist.domain.enums.MessageRoles;

@Component
public class RoleMap {
    public String mapRole(MessageRoles role) {
        return switch (role) {
            case SYSTEM -> "system";
            case USER -> "user";
            case ASSISTANT -> "assistant";
            case TOOL -> "tool";
        };
    }
}
