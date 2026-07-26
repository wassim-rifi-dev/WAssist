package dev.wassim.wassist.domain.dto;

import dev.wassim.wassist.domain.enums.MessageRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentMessage {
    private MessageRoles role;
    private String content;
}
