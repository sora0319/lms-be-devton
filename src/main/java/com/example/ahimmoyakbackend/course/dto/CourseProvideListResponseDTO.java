package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.company.common.CourseProvideState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseProvideListResponseDTO {
    private LocalDate beginDate;
    private LocalDate endDate;
    private CourseProvideState state;
    private int attendeeCount;
    private String jobTitle; // 이건 뭐임
    private String applicantName;
    private String applicantPhone;
    private String applicantEmail;
    private LocalDate applicationDate;
    private List<CourseLearnerResponseDTO> learners;
}