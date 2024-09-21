package com.example.ahimmoyakbackend.company.dto;

import com.example.ahimmoyakbackend.company.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateResponseDto {
    private String name;
    private String ownerName;
    private String businessNumber;
    private String email;
    private String phone;

    public static CompanyUpdateResponseDto toDto(Company company) {
        return CompanyUpdateResponseDto.builder()
                .name(company.getCompanyName())
                .ownerName(company.getOwnerName())
                .businessNumber(company.getBusinessNumber())
                .email(company.getEmail())
                .phone(company.getPhone())
                .build();

    }
}
