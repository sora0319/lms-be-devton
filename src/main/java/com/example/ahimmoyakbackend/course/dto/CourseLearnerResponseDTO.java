package com.example.ahimmoyakbackend.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseLearnerResponseDTO {
    private String department;
    private String username;
    private String name;
    private String email;
    private String birth;
}
