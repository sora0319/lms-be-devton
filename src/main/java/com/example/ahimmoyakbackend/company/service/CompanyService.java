package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.company.dto.*;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.company.entity.Department;
import com.example.ahimmoyakbackend.company.repository.AffiliationRepository;
import com.example.ahimmoyakbackend.company.repository.CompanyRepository;
import com.example.ahimmoyakbackend.company.repository.DepartmentRepository;
import com.example.ahimmoyakbackend.global.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final AffiliationRepository affiliationRepository;
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<CompanyInquiryEmployeeListResponseDto> getUserbyCompany(Long companyId) {

        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("해당 companyId 가 없습니다"));

        List<Affiliation> affiliations;
        affiliations = affiliationRepository.findAllByDepartment_Company_IdAndApprovalTrue(companyId);

        return affiliations.stream()
                .map(CompanyInquiryEmployeeListResponseDto::toDto)
                .collect(Collectors.toList());

    }

    @Transactional
    public CompanyInquiryUserDetailResponseDto getUserDetail(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("userId 가 없습니다."));
        Affiliation affiliation = user.getAffiliation();
        List<Address> addresses = user.getAddresses();

        return CompanyInquiryUserDetailResponseDto.toDto(affiliation, addresses);
    }
    
    @Transactional
    public CompanyDeleteUserResponseDto deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("userId 가 없습니다."));
        Affiliation affiliation = user.getAffiliation();

        Affiliation updateAffiliation = Affiliation.builder()
                .id(affiliation.getId())
                .isSupervisor(affiliation.getIsSupervisor())
                .approval(false)
                .department(null)
                .user(affiliation.getUser())
                .build();

        affiliationRepository.save(updateAffiliation);

        return CompanyDeleteUserResponseDto.builder()
                .msg("사원 삭제가 완료되었습니다")
                .build();
    }

    @Transactional
    public CompanyEnrollDepartmentResponseDto enrollDepartment(Long affiliationId, Long companyId, CompanyEnrollDepartmentRequestDto requestDto) {

        Affiliation affiliation = affiliationRepository.findById(affiliationId).orElseThrow(() -> new IllegalArgumentException("해당 affiliationId 가 없습니다."));
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("해당 companyId 가 없습니다."));

        Department department = Department.builder()
                .name(requestDto.getDepartmentName())
                .company(company)
                .build();

        departmentRepository.save(department);

        Affiliation updatedAffiliation = Affiliation.builder()
                .id(affiliation.getId())
                .user(affiliation.getUser())
                .isSupervisor(affiliation.getIsSupervisor())
                .approval(affiliation.getApproval())
                .department(affiliation.getDepartment())
                .build();

        affiliationRepository.save(updatedAffiliation);

        return CompanyEnrollDepartmentResponseDto.builder()
                .msg("부서 등록이 완료되었습니다")
                .build();

    }

    @Transactional
    public CompanyDeleteDepartmentResponseDto deleteDepartment(Long companyId, Long affiliationId, Long departmentId) {

        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("해당 companyId 가 없습니다"));
        Affiliation affiliation = affiliationRepository.findById(affiliationId).orElseThrow(() -> new IllegalArgumentException("해당 affiliationId 가 없습니다"));
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new IllegalArgumentException("해당 departmentId 가 없습니다"));

        Affiliation updateAffiliation = Affiliation.builder()
                .id(affiliation.getId())
                .isSupervisor(affiliation.getIsSupervisor())
                .approval(affiliation.getApproval())
                .department(null)
                .user(affiliation.getUser())
                .build();

        affiliationRepository.save(updateAffiliation);

        departmentRepository.delete(department);

        return CompanyDeleteDepartmentResponseDto.builder()
                .msg("부서삭제완료")
                .build();
    }

    @Transactional
    public CompanyUpdateDepartmentResponseDto updateDepartment(Long companyId, Long departmentId, CompanyUpdateDepartmentRequestDto requestDto) {

        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("해당 companyId 가 없습니다"));
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new IllegalArgumentException("해당 departmentId 가 없습니다"));
        department.patch(requestDto);
        Department updated = departmentRepository.save(department);

        return CompanyUpdateDepartmentResponseDto.toDto(updated);
    }

    @Transactional(readOnly = true)
    public List<CompanyInquiryDepartmentResponseDto> getDepartmentCompanyId(Long companyId) {
        List<Department> departments = departmentRepository.findByCompanyId(companyId);


        return departments.stream()
                .map(department -> CompanyInquiryDepartmentResponseDto.builder()
                        .departmentId(department.getId())
                        .departmentName(department.getName())
                        .companyId(department.getCompany().getId())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public CompanyEnrollResponseDto enrollCompany(Long userId, CompanyEnrollRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 userId 가 없습니다"));

        if (companyRepository.existsByBusinessNumber(requestDto.getBusinessNumber())) {
            throw new IllegalArgumentException("이미 존재하는 사업자 등록번호입니다.");
        }

        if (companyRepository.existsByEmailDomain(requestDto.getEmailDomain())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 도메인 입니다.");
        }
        Company company = Company.builder()
                .name(requestDto.getCompanyName())
                .ownerName(requestDto.getOwnerName())
                .businessNumber(requestDto.getBusinessNumber())
                .email(requestDto.getEmail())
                .emailDomain(requestDto.getEmailDomain())
                .phone(requestDto.getPhoneNumber())
                .build();

        companyRepository.save(company);

        // department update
        Department department = Department.builder()
                .name("default department")
                .company(company)
                .build();

        departmentRepository.save(department);

        // affiliation update
        Affiliation affiliation = Affiliation.builder()
                .user(user)
                .department(department)
                .approval(false)
                .isSupervisor(true)
                .build();

        affiliationRepository.save(affiliation);


        return CompanyEnrollResponseDto.builder()
                .msg("회사 등록 완료")
                .build();
    }

    @Transactional
    public CompanyUpdateResponseDto updateCompany(Long companyId, CompanyUpdateRequestDto requestDto) {

        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("해당 companyId 가 없습니다"));
        company.patch(requestDto);
        Company updated = companyRepository.save(company);
        return CompanyUpdateResponseDto.toDto(updated);
    }

    @Transactional
    public List<FindCompanyResponseDto> findCompanyName(FindCompanyRequestDto requestDto) {

        List<Company> companies = companyRepository.findByNameContaining(requestDto.getCompanyName());

        if (companies.isEmpty()) {
            return Collections.singletonList(FindCompanyResponseDto.builder()
                    .msg("검색한 회사가 없습니다.")
                    .build());
        }

        return companies.stream()
                .map(company -> FindCompanyResponseDto.builder()
                        .id(company.getId())
                        .companyName(company.getName())
                        .build())
                .collect(Collectors.toList());

    }

    @Transactional
    public FindCompanyIdDto getCompanyIdFromToken(User user) {
        Long companyId = user.getAffiliation().getDepartment().getCompany().getId();
        return FindCompanyIdDto.builder()
                .id(companyId)
                .build();
    }
    
}
