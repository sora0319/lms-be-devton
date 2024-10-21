package com.example.ahimmoyakbackend.company.dto;

import com.example.ahimmoyakbackend.auth.common.Gender;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.Department;
import com.example.ahimmoyakbackend.global.entity.Address;
import com.example.ahimmoyakbackend.global.repository.AddressRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CompanyInquiryUserResponseDto {

    private static AddressRepository addressRepository;

    private final Long id;
    private final String username;
    private final String name;
    private final LocalDate birth;
    private final String phone;
    private final String email;
    private final Gender gender;
    private final Boolean approval;
    private final String departmentName;
    private final List<CompanyGetAddressDto> addresses;

    public static CompanyInquiryUserResponseDto toDto(Affiliation affiliation, List<Address> addresses) {
        User user = affiliation.getUser();
        Department department = affiliation.getDepartment();

        List<CompanyGetAddressDto> addressDtos = addresses.stream()
                .map(CompanyGetAddressDto::fromEntity)
                .collect(Collectors.toList());

        return CompanyInquiryUserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .birth(user.getBirth())
                .phone(user.getPhone())
                .email(user.getEmail())
                .gender(user.getGender())
                .approval(affiliation != null ? affiliation.getApproval() : null)
                .departmentName(department.getName())
                .addresses(addressDtos != null ? addressDtos : null)
                .build();
    }

}
