package com.example.ahimmoyakbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostMessageShowResponseDto {

    private String title;

    private String content;

    private String senderName;

    private LocalDateTime createAt;

    private String receiverName;

    private Boolean isRead;

}
