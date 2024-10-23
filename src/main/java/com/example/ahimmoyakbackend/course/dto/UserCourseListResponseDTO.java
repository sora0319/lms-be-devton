package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.course.common.CourseCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseListResponseDTO {
    private Long courseId;
    private String title;
    private String institutionName;
    private CourseCategory category;
    private LocalDate beginDate;
    private LocalDate endDate;
    // 진행률 추가 해야함.
}