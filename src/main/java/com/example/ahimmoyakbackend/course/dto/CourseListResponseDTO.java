package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.global.entity.Image;
import com.example.ahimmoyakbackend.institution.entity.Tutor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseListResponseDTO {
    private Long id;
    private String title;
    private Image image;
    private CourseCategory category;
    private Tutor tutor;
}
