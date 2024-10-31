package com.example.ahimmoyakbackend.board.dto;

import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BoardPageResponseDto(
        String title,
        String content,
        String user,
        List<CommentListResponseDto> comments,
        LocalDateTime createdAt
) {
    public static BoardPageResponseDto from(CourseBoard board, List<CommentListResponseDto> comments) {
        return BoardPageResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .user(board.getUser().getUsername())
                .comments(comments)
                .createdAt(board.getCreatedAt())
                .build();
    }
}
