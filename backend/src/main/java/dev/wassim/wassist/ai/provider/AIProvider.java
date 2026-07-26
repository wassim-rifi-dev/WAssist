package dev.wassim.wassist.ai.provider;

import dev.wassim.wassist.domain.dto.response.AgentResponse;

public interface AIProvider {
    AgentResponse chat(String prompt);
}
