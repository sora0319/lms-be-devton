package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.course.common.CourseState;
import com.example.ahimmoyakbackend.course.entity.Course;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseCreateRequestDto(
   String title,
   String introduction,
   LocalDate beginDate,
   LocalDate endDate
) {
    public Course toEntity(User tutor) {
        return Course.builder()
                .title(this.title)
                .introduction(this.introduction)
                .beginDate(this.beginDate)
                .endDate(this.endDate)
                .state(CourseState.NOT_STARTED)
                .tutor(tutor)
                .build();
    }
}
