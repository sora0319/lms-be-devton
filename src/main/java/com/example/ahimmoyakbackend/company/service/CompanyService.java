package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.company.dto.*;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.company.entity.Department;
import com.example.ahimmoyakbackend.company.repository.AffiliationRepository;
import com.example.ahimmoyakbackend.company.repository.CompanyRepository;
import com.example.ahimmoyakbackend.company.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final AffiliationRepository affiliationRepository;
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

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
                .department(department)
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

        Company company = companyRepository.findById(companyId).orElseThrow(()-> new IllegalArgumentException("해당 companyId 가 없습니다"));
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

    public CompanyEnrollResponseDto enrollCompany(CompanyEnrollRequestDto requestDto) {
        
        Company company = Company.builder()
                .name(requestDto.getCompanyName())
                .ownerName(requestDto.getOwnerName())
                .businessNumber(requestDto.getBusinessNumber())
                .email(requestDto.getEmail())
                .phone(requestDto.getPhoneNumber())
                .build();
        
        companyRepository.save(company);
        
        return CompanyEnrollResponseDto.builder()
                .msg("회사 등록 완료")
                .build();
    }

    public CompanyUpdateResponseDto updateCompany(Long companyId, CompanyUpdateRequestDto requestDto) {

        Company company = companyRepository.findById(companyId).orElseThrow(()->new IllegalArgumentException("해당 companyId 가 없습니다"));
        company.patch(requestDto);
        Company updated = companyRepository.save(company);
        return CompanyUpdateResponseDto.toDto(updated);
    }


//    @Transactional
//    public List<CompanyInquiryUserResponseDto> getUserByCompany(Long companyId, Long affiliationId, Long departmentId) {
//        List<Affiliation> affiliations = affiliationRepository.findByCompanyIdAndAffiliationIdAndDepartmentId(companyId, affiliationId, departmentId);
//
//        return affiliations.stream()
//                .map(affiliation -> new CompanyInquiryUserResponseDto(affiliations))
//                .collect(Collectors.toList());
//    }


}
