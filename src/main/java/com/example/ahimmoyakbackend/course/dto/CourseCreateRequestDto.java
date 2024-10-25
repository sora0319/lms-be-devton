package com.example.ahimmoyakbackend.course.dto;

import lombok.Builder;

@Builder
public record CourseCreateRequestDto(
   String title,
   String introduction
) {}
