package dev.wassim.wassist.features.chat.controller;

import org.springframework.web.bind.annotation.RestController;

import dev.wassim.wassist.common.constants.ApiPaths;
import dev.wassim.wassist.features.chat.dto.request.ChatRequest;
import dev.wassim.wassist.features.chat.dto.response.ChatResponse;
import dev.wassim.wassist.features.chat.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(ApiPaths.CHAT)
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("ask")
    public ResponseEntity<ChatResponse> ask(
        @RequestBody @Valid ChatRequest chatRequest
    ) {
        ChatResponse response = chatService.ask(chatRequest);
        return ResponseEntity.ok(response);
    }
    
}
