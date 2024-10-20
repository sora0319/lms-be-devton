package com.example.ahimmoyakbackend.company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEnrollRequestDto {

    @NotBlank(message = "회사 이름을 입력하세요")
    private String companyName;

    @NotBlank(message = "대표자명을 입력하세요")
    private String ownerName;

    @NotBlank(message = "법인등록번호를 입력하세요")
    @Pattern( message = "유효하지 않은 등록번호 형식 입니다."
            ,regexp = "^\\\\d{6}-?\\\\d{7}$")
    private String businessNumber;

    @NotBlank(message = "이메일을 입력하세요")
    @Email(message = "유효하지 않은 이메일 형식 입니다.")
    private String email;

    @NotBlank(message = "회사 도메인을 입력하세요")
    private String emailDomain;

    @NotBlank(message = "전화번호를 입력하세요")
    private String phoneNumber;

}
