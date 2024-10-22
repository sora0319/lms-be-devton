package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.course.common.CourseCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailResponseDTO {
    private String imagePath;
    private String title;
    private String courseIntroduction;
    private CourseCategory category;
    private List<CurriculumListResponseDTO> curriculumList;
    private String tutorName;
    private String tutorIntroduction;
}
