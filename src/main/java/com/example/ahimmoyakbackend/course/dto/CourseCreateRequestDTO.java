package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.global.entity.Image;
import com.example.ahimmoyakbackend.institution.entity.Tutor;
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
    private Image image;
    private CourseCategory category;
    private String tutorName;

    public static CourseCreateRequestDTO from(Course course, Tutor tutor){
        return CourseCreateRequestDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .introduction(course.getIntroduction())
                .image(course.getImage())
                .category(course.getCategory())
                .tutorName(tutor.getUser().getName())
                .build();
    }

}
