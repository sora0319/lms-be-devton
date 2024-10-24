package com.example.ahimmoyakbackend.company.dto;

import com.example.ahimmoyakbackend.auth.common.Gender;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.Department;
import com.example.ahimmoyakbackend.global.entity.Address;
import com.example.ahimmoyakbackend.global.repository.AddressRepository;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CompanyInquiryEmployeeListResponseDto {

    private static AddressRepository addressRepository;

    private final Long id;
    private final String name;
    private final String departmentName;
    private final Gender gender;
    private final String email;

    public static CompanyInquiryEmployeeListResponseDto toDto(Affiliation affiliation) {
        User user = affiliation.getUser();
        Department department = affiliation.getDepartment();

        return CompanyInquiryEmployeeListResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .gender(user.getGender())
                .departmentName(department.getName())
                .build();
    }

}
