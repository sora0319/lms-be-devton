package com.example.ahimmoyakbackend.live.dto;

import com.example.ahimmoyakbackend.live.entity.ChatMessage;
import lombok.Builder;

@Builder
public record ChatMessagePubDto(
        String username,
        String message
) {
    public ChatMessage to(long roomId) {
        return ChatMessage.builder()
                .liveId(roomId)
                .username(this.username)
                .message(this.message)
                .build();
    }
}
