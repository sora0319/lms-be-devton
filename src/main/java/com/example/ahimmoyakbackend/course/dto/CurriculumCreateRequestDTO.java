package com.example.ahimmoyakbackend.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumCreateRequestDTO {
    private String title;
    private Integer idx;
}
