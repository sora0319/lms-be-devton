package com.example.ahimmoyakbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostMessageResponseDto {

    private String title;

    private String content;

    private String senderName;

    private String receiverName;

    private Boolean isRead;

}
