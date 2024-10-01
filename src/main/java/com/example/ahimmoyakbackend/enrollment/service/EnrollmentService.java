package com.example.ahimmoyakbackend.enrollment.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import com.example.ahimmoyakbackend.company.repository.AffiliationRepository;
import com.example.ahimmoyakbackend.company.repository.CompanyRepository;
import com.example.ahimmoyakbackend.company.repository.CourseProvideRepository;
import com.example.ahimmoyakbackend.course.entity.Enrollment;
import com.example.ahimmoyakbackend.course.repository.EnrollmentRepository;
import com.example.ahimmoyakbackend.enrollment.dto.*;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import com.example.ahimmoyakbackend.institution.entity.Manager;
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
    private final CourseProvideRepository courseProvideRepository;
    private final ManagerRepository managerRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AffiliationRepository affiliationRepository;
    private final UserRepository userRepository;

    public List<EnrollmentGetListResponseDTO> getEnrollmentList(User user, Long institutionId, Long companyId) {

        Manager manager = managerRepository.findByUser(user);
        Institution institution = manager.getInstitution();

        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("회사가 없습니다."));

        List<CourseProvide> courseProvideList = courseProvideRepository.findAllByCompanyAndCourse_Institution(company, institution);

        return courseProvideList.stream().map(contract -> {
            LocalDate endDate = contract.getEndDate();
            LocalDate beginDate = contract.getBeginDate();
            LocalDate currentDate = LocalDate.now();
            LocalDate remainDate = endDate.isAfter(currentDate) ? LocalDate.ofEpochDay(ChronoUnit.DAYS.between(currentDate, endDate)) : LocalDate.ofEpochDay(0);

            return EnrollmentGetListResponseDTO.builder()
                    .title(contract.getCourse().getTitle())
                    .institutionName(institution.getName())
                    .instructorName(contract.getCourse().getTutor().getUser().getName())
                    .attendeeCount(contract.getAttendeeCount())
                    .beginDate(beginDate)
                    .endDate(endDate)
                    .remainDate(remainDate)
                    .build();
        }).collect(Collectors.toList());


    }

    public EnrollmentClassRegistrationResponseDTO registration(EnrollmentClassRegistrationRequestDTO dto) {

        Enrollment enrollment = enrollmentRepository.findByContract_Company_IdAndContract_Course_Id(dto.getCompanyId(), dto.getCourseId());

        CourseProvide courseProvide = courseProvideRepository.findById(dto.getCourse_providerId()).orElseThrow(() -> new IllegalArgumentException("계약 아이디가없습니다."));


        enrollment.assignCourseProvide(courseProvide);

        enrollmentRepository.save(enrollment);

        return EnrollmentClassRegistrationResponseDTO.builder()
                .msg("수강등록 완료")
                .build();


    }

    public EnrollmentClassCancelResponseDTO cancelEnrollment(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("수강신청 아이디가 없습니다."));

        enrollment.cancelCourseProvide();

        enrollmentRepository.save(enrollment);

        return EnrollmentClassCancelResponseDTO.builder()
                .msg("수강 인원 삭제 완료")
                .build();
    }

    public EnrollmentTossRosterResponseDTO tossRoster(User user, EnrollmentTossRosterRequestDTO dto) {

        Long companyId = user.getAffiliation().getDepartment().getCompany().getId();

        List<Affiliation> affiliation = affiliationRepository.findAllByDepartment_Company_Id(companyId);

        List<String> affiliationList =
                affiliation.stream().map(aff -> aff.getUser().getName()).toList();

        List<String> filteredNames = affiliationList.stream()
                .filter(dto.getUserName()::contains)
                .toList();


        return EnrollmentTossRosterResponseDTO.builder()
                .msg("사원 명단이 정상적으로 넘겨졌습니다.")
                .userName(filteredNames)
                .build();


    }
}
