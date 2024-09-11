package com.example.ahimmoyakbackend.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInquiryUserRequestDto {

    private Long id;

    private String name;

    private String ownerName;

    private String businessNumber;

    private String email;

    private String emailDomain;

    private String phone;

}
