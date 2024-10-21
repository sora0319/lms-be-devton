package com.example.ahimmoyakbackend.board.dto;

import com.example.ahimmoyakbackend.board.common.BoardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardInquiryResponseDto {

    private String username;

    private String title;

    private String content;

    private BoardType type;

    private Long boardId;

    private LocalDateTime createAt;

}
