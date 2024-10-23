package com.example.ahimmoyakbackend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeJoinRequestDTO {
    private String departmentId;
    private String companyId;
}
