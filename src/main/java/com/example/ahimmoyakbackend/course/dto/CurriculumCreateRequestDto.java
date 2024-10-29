package com.example.ahimmoyakbackend.course.dto;

import lombok.Builder;

@Builder
public record CurriculumCreateRequestDto(
        String title
) {
}
