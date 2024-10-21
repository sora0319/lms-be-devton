package com.example.ahimmoyakbackend.company.dto;

import com.example.ahimmoyakbackend.company.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInquiryDepartmentResponseDto {

    private Long departmentId;
    private String departmentName;
    private Long companyId;

    public static CompanyInquiryDepartmentResponseDto toDto(Department departments) {
        return CompanyInquiryDepartmentResponseDto.builder()
                .departmentId(departments.getId())
                .departmentId(departments.getId())
                .build();
    }
}
