package com.example.ahimmoyakbackend.board.dto;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import com.example.ahimmoyakbackend.board.entity.CourseComment;
import lombok.Builder;

@Builder
public record CommentWriteRequestDto(
        String comment
) {
    public CourseComment toEntity(User user, CourseBoard courseBoard) {
        return CourseComment.builder()
                .content(this.comment)
                .user(user)
                .courseBoard(courseBoard)
                .build();
    }
}
