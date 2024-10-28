package com.example.ahimmoyakbackend.board.dto;

import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import lombok.Builder;

@Builder
public record BoardListResponseDto(
        String title,
        String user,    // 유저 이름
        long comment     // 댓글 개수
) {
    public static BoardListResponseDto from(CourseBoard board, long count) {
        return BoardListResponseDto.builder()
                .title(board.getTitle())
                .user(board.getUser().getName())
                .comment(count)
                .build();
    }
}
