package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.course.common.CourseCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TutorGetCourseListResponseDTO {
    private Long id;
    private String title;
    private String image;
    private CourseCategory category;
}
