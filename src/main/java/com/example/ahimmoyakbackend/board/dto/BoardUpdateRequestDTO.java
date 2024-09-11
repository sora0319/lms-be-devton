package com.example.ahimmoyakbackend.board.dto;


import com.example.ahimmoyakbackend.board.common.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateRequestDTO {

    private Long id;

    private String title;

    private String content;

    private BoardType type;
}
