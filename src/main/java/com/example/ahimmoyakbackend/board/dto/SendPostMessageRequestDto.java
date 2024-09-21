package com.example.ahimmoyakbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendPostMessageRequestDto {

    private String title;

    private String content;

    private String sender;

    private List<TargetUserRequestDto> targetNames;

}