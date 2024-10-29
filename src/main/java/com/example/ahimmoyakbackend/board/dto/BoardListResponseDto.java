package com.example.ahimmoyakbackend.board.dto;

import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import lombok.Builder;

@Builder
public record BoardListResponseDto(
        String title,
        String user,    // 유저 이름
        long comment,     // 댓글 개수
        long boardId
) {
    public static BoardListResponseDto from(CourseBoard board, long count) {
        return BoardListResponseDto.builder()
                .title(board.getTitle())
                .user(board.getUser().getUsername())
                .comment(count)
                .boardId(board.getId())
                .build();
    }
}
