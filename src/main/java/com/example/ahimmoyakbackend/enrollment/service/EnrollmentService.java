package com.example.ahimmoyakbackend.enrollment.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.company.entity.Contract;
import com.example.ahimmoyakbackend.company.repository.CompanyRepository;
import com.example.ahimmoyakbackend.company.repository.ContractRepository;
import com.example.ahimmoyakbackend.course.repository.EnrollmentRepository;
import com.example.ahimmoyakbackend.enrollment.dto.EnrollmentGetListResponseDTO;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import com.example.ahimmoyakbackend.institution.entity.Manager;
import com.example.ahimmoyakbackend.institution.repository.InstitutionRepository;
import com.example.ahimmoyakbackend.institution.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final CompanyRepository companyRepository;
    private final ContractRepository contractRepository;
    private final ManagerRepository managerRepository;

    public List<EnrollmentGetListResponseDTO> getEnrollmentList(User user, Long institutionId, Long companyId) {

        Manager manager = managerRepository.findByUser(user);
        Institution institution = manager.getInstitution();

        Company company = companyRepository.findById(companyId).orElseThrow(()-> new IllegalArgumentException("회사가 없습니다."));

        List<Contract> contractList = contractRepository.findAllByCompanyAndCourse_Institution(company, institution);

        return contractList.stream().map(contract -> {
            LocalDate endDate = contract.getEndDate();
            LocalDate beginDate = contract.getBeginDate();
            LocalDate currentDate = LocalDate.now();
            LocalDate remainDate = endDate.isAfter(currentDate) ? LocalDate.ofEpochDay(ChronoUnit.DAYS.between(currentDate, endDate)) : LocalDate.ofEpochDay(0);

            return EnrollmentGetListResponseDTO.builder()
                    .title(contract.getCourse().getTitle())
                    .institutionName(institution.getName())
                    .instructorName(contract.getCourse().getTutor().getUser().getName())
                    .attendeeCount(contract.getAttendeeAmount())
                    .beginDate(beginDate)
                    .endDate(endDate)
                    .remainDate(remainDate)
                    .build();
        }).collect(Collectors.toList());


    }
}
