package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegistrationRequestDTO {
    private LocalDate beginDate;
    private LocalDate endDate;
    private int attendeeCount;
}