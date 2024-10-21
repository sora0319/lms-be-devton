package com.example.ahimmoyakbackend.board.dto;

import com.example.ahimmoyakbackend.board.common.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseBoardsResponseDto {

    private String username;

    private String title;

    private BoardType type;

    private Long boardId;

    private LocalDateTime createdAt;

}