package com.example.ahimmoyakbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostMessageResponseDto {

    private String title;

    private String content;

    private String senderName;

    private LocalDateTime createdAt;

    private List<String> receiverName;

    private Boolean isRead;

}
