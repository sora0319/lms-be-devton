package com.example.ahimmoyakbackend.enrollment.dto;

import com.example.ahimmoyakbackend.company.entity.Contract;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentClassRegistrationRequestDTO {

    private Long companyId;
    private Long courseId;
    private Long course_providerId;

}
