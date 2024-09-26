package com.example.ahimmoyakbackend.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseProvideListResponseDTO {
    private LocalDate beginDate;
    private LocalDate endDate;
    private int attendeeCount;
}