package com.example.ahimmoyakbackend.board.dto;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import com.example.ahimmoyakbackend.course.entity.Course;

public record BoardCreateRequestDto(
        String title,
        String content
) {
    public CourseBoard toEntity(User user, Course course, BoardType type) {
        return CourseBoard.builder()
                .title(this.title)
                .content(this.content)
                .type(type)
                .course(course)
                .user(user)
                .build();
    }
}
