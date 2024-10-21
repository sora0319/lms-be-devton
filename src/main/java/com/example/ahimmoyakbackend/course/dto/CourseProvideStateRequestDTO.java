package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.company.common.CourseProvideState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseProvideStateRequestDTO {
    private CourseProvideState state;
}
