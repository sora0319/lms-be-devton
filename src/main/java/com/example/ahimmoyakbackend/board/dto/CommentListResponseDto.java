package com.example.ahimmoyakbackend.board.dto;

import com.example.ahimmoyakbackend.board.entity.CourseComment;
import lombok.Builder;

@Builder
public record CommentListResponseDto(
    String content,
    String user
) {
    public static CommentListResponseDto from(CourseComment comment) {
        return CommentListResponseDto.builder()
                .content(comment.getContent())
                .user(comment.getUser().getUsername())
                .build();
    }
}
