package com.example.ahimmoyakbackend.enrollment.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import com.example.ahimmoyakbackend.company.repository.AffiliationRepository;
import com.example.ahimmoyakbackend.company.repository.CompanyRepository;
import com.example.ahimmoyakbackend.company.repository.CourseProvideRepository;
import com.example.ahimmoyakbackend.course.common.EnrollmentState;
import com.example.ahimmoyakbackend.course.entity.Enrollment;
import com.example.ahimmoyakbackend.course.repository.EnrollmentRepository;
import com.example.ahimmoyakbackend.enrollment.dto.*;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import com.example.ahimmoyakbackend.institution.entity.Manager;
import com.example.ahimmoyakbackend.institution.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

    public List<EnrollmentGetListResponseDTO> getEnrollmentList(User user, Long companyId) {
        List<Company> companyList;
        if (companyId == null) {
            System.out.println("없음");
            companyList = companyRepository.findAll();
        } else {
            System.out.println("있음");
            companyList = companyRepository.findAllById(companyId);

        }

        Manager manager = managerRepository.findByUser(user);
        Institution institution = manager.getInstitution();
        List<CourseProvide> courseProvideList = new ArrayList<>();
        for (Company company : companyList) {
            courseProvideList.add(courseProvideRepository.findByCompanyAndCourse_Institution(company, institution));
        }

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

        Enrollment enrollment = enrollmentRepository.findByCourseProvide_Company_IdAndCourseProvide_Course_Id(dto.getCompanyId(), dto.getCourseId());

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

        // DTO에서 넘어온 체크된 사원 명단 (이름 or ID)
        List<String> selectedUserNames = dto.getUserName();

        // 해당 회사 소속의 모든 사용자 정보 조회
        List<User> users = userRepository.findAllByAffiliation_Department_Company_Id(user.getAffiliation().getDepartment().getCompany().getId());

        // 선택된 사원들을 Enrollment에 저장
        List<Enrollment> enrollments = selectedUserNames.stream()
                .map(userName -> {
                    // User 엔티티에서 이름에 해당하는 사원을 찾아 Enrollment로 저장
                    User selectedUser = users.stream()
                            .filter(u -> u.getName().equals(userName))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("해당 사원이 존재하지 않습니다."));

                    // Enrollment 객체 생성 (빌더 패턴 사용)
                    Enrollment enrollment = Enrollment.builder()
                            .user(selectedUser)
                            .state(EnrollmentState.ONPROGRESS)
                            .build();

                    // Enrollment 저장
                    enrollmentRepository.save(enrollment);
                    return enrollment;
                })
                .toList();

        // 응답 생성
        return EnrollmentTossRosterResponseDTO.builder()
                .msg("선택한 사원들이 정상적으로 등록되었습니다.")
                .userName(selectedUserNames)  // 선택한 사원들의 이름 리스트 반환
                .build();

    }

    @Transactional
    public List<EnrollmentReturnCompanyListResponseDTO> returnCompanyList(User user) {
        Manager manager = managerRepository.findByUser(user);

        List<CourseProvide> courseProvideList = manager.getInstitution().getCourseProvide();
        for (CourseProvide courseProvide : courseProvideList) {
            courseProvide.getCompany();
        }


        List<EnrollmentReturnCompanyListResponseDTO> responseDTOs = courseProvideList.stream()
                .map(courseProvide -> EnrollmentReturnCompanyListResponseDTO.builder()
                        .companyName(courseProvide.getCompany().getName())  // 회사 이름 가져오기
                        .companyId(courseProvide.getCompany().getId())      // 회사 ID 가져오기
                        .build())
                .toList();

        return responseDTOs;

    }
}
