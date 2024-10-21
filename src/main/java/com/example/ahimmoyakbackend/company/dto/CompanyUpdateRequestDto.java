package com.example.ahimmoyakbackend.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateRequestDto {
    private String name;
    private String ownerName;
    private String email;
    private String phone;
}