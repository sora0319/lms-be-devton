package com.example.ahimmoyakbackend.company.dto;

import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.company.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateDepartmentResponseDto {
    private Long departmentId;
    private String departmentName;
    private Company company;

    public static CompanyUpdateDepartmentResponseDto toDto(Department department) {
        return CompanyUpdateDepartmentResponseDto.builder()
                .departmentId(department.getId())
                .departmentName(department.getName())
                .company(department.getCompany())
                .build();
    }
}
