package com.example.ahimmoyakbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostMessageRequestDto {

    private String title;

    private String content;

    private String senderName;

    private String receiverName;

}