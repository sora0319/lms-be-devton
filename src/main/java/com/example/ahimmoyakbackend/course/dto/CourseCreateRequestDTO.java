package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.common.TrainingCourseType;
import com.example.ahimmoyakbackend.global.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateRequestDTO {

    private Long id;
    private String title;
    private String introduction;
    private String  image;
    private CourseCategory category;
    private String tutorName;
    private TrainingCourseType type;


}
