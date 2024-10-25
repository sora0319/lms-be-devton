package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.course.entity.Course;
import lombok.Builder;

@Builder
public record CourseListResponseDto(
   long id,
   String title,
   String introduction,
   String tutor
) {
    public static CourseListResponseDto from(Course course) {
        return CourseListResponseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .introduction(course.getIntroduction())
                .tutor(course.getTutor().getName())
                .build();
    }
}
