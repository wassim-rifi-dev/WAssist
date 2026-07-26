package dev.wassim.wassist.domain.conversation;

import java.util.ArrayList;
import java.util.List;

import dev.wassim.wassist.domain.dto.AgentMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {
    private List<AgentMessage> messages = new ArrayList<>();
}
