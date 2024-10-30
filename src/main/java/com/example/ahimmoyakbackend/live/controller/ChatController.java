package com.example.ahimmoyakbackend.live.controller;

import com.example.ahimmoyakbackend.live.dto.ChatMessagePubDto;
import com.example.ahimmoyakbackend.live.dto.ChatMessageSubDto;
import com.example.ahimmoyakbackend.live.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat/{liveId}")
    public ResponseEntity<ChatMessageSubDto> message(@PathVariable long liveId, ChatMessagePubDto dto) {
        ChatMessageSubDto subDto = chatService.message(liveId, dto);
        if (subDto == null) {
            return ResponseEntity.notFound().build();
        }
        messagingTemplate.convertAndSend("/topic/chat/" + liveId, subDto);
        return ResponseEntity.ok(subDto);
    }
}
