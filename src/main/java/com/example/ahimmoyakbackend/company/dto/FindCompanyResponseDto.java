package com.example.ahimmoyakbackend.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindCompanyResponseDto {
    private String msg;
    private Long id;
    private String companyName;
}
