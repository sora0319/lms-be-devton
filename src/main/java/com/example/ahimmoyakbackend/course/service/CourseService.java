package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import com.example.ahimmoyakbackend.company.repository.AffiliationRepository;
import com.example.ahimmoyakbackend.company.repository.CourseProvideRepository;
import com.example.ahimmoyakbackend.contents.dto.ContentsInquiryResponseDTO;
import com.example.ahimmoyakbackend.course.common.ContentsHistoryState;
import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.dto.*;
import com.example.ahimmoyakbackend.course.entity.*;
import com.example.ahimmoyakbackend.course.repository.ContentsHistoryRepository;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.course.repository.EnrollmentRepository;
import com.example.ahimmoyakbackend.curriculum.dto.CurriculumInquiryResponseDTO;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import com.example.ahimmoyakbackend.institution.entity.Manager;
import com.example.ahimmoyakbackend.institution.entity.Tutor;
import com.example.ahimmoyakbackend.institution.repository.ManagerRepository;
import com.example.ahimmoyakbackend.institution.repository.TutorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ContentsHistoryRepository contentsHistoryRepository;
    private final CourseProvideRepository courseProvideRepository;
    private final TutorRepository tutorRepository;
    private final AffiliationRepository affiliationRepository;
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public CourseDetailsInquiryResponseDTO inquiry(User user, Long courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("코스가 없습니다."));
        CourseProvide courseProvide = courseProvideRepository.findByCourseAndEnrollments_User(course, user).orElseThrow(() -> new IllegalArgumentException("컨텐츠가 없습니다."));
        CourseDetailsInquiryResponseDTO courseDto = CourseDetailsInquiryResponseDTO.from(course, courseProvide);

        List<CurriculumInquiryResponseDTO> curriculumDTOList = new ArrayList<>();
        int courseTotalContents = 0;
        int courseCompletedContents = 0;

        for (Curriculum curriculum : course.getCurriculumList()) {

            CurriculumInquiryResponseDTO curriculumDto = CurriculumInquiryResponseDTO.from(curriculum);
            List<ContentsInquiryResponseDTO> contentsDTOList = new ArrayList<>();

            int completedContents = 0;

            for (Contents contents : curriculum.getContentsList()) {

                ContentsHistory history = contentsHistoryRepository.findByContentsAndEnrollment_User(contents, user).orElseThrow(() -> new IllegalArgumentException("컨텐츠의 히스토리가 없습니다."));
                ContentsInquiryResponseDTO contentsDTO = ContentsInquiryResponseDTO.from(contents, history.getState());
                courseTotalContents++;

                if (history.getState().equals(ContentsHistoryState.COMPLETED)) {
                    courseCompletedContents++;
                    completedContents++;
                }
                contentsDTOList.add(contentsDTO);
            }
            curriculumDto.setCompletedContents(completedContents)
                    .setContents(contentsDTOList);
            curriculumDTOList.add(curriculumDto);
        }
        courseDto.setCompletedContents(courseCompletedContents)
                .setTotalContents(courseTotalContents)
                .setCurriculums(curriculumDTOList);
        return courseDto;

    }

    @Transactional
    public CourseCreateResponseDTO create(User user, CourseCreateRequestDTO dto) {
        Tutor tutor = tutorRepository.findByUser_Name(dto.getTutorName());
        Institution institution = user.getManager().getInstitution();

        Course newCourse = Course.builder()
                .title(dto.getTitle())
                .introduction(dto.getIntroduction())
                //.image(course.getImage())
                .institution(institution)
                .category(dto.getCategory())
                .type(dto.getType())
                .tutor(tutor)
                .build();

        courseRepository.save(newCourse);

        return CourseCreateResponseDTO.builder()
                .msg("코스 생성 성공")
                .build();

    }

    @Transactional
    public CourseModifyResponseDTO modify(Long courseId, CourseModifyRequestDTO dto) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("코스가 존재 하지 않습니다."));


        Course course1 = course.patch(dto);

        courseRepository.save(course1);

        return CourseModifyResponseDTO.builder()
                .msg("수정 되었습니다.")
                .build();

    }

    @Transactional
    public CourseDeleteResponseDTO delete(Long courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("코스가 존재 하지 않습니다."));

        courseRepository.delete(course);

        return CourseDeleteResponseDTO.builder()
                .msg("삭제 되었습니다.")
                .build();
    }

    // 마이페이지 코스리스트 조회
    @Transactional
    public Page<CourseListResponseDTO> findUserCourseList(User user, Long institutionId, int page, @Positive int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (user.getId() == null || institutionId == null) {
            return null;
        }
        Page<Course> coursePage = courseRepository.findAll(pageable);
        List<CourseListResponseDTO> list = coursePage.stream()
                .map(course -> CourseListResponseDTO.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .image(course.getImage().getPath())
                        .build())
                .toList();
        return new PageImpl<>(list, coursePage.getPageable(), coursePage.getTotalElements());
    }

    // 메인페이지 랜덤코스 탐색
    @Transactional
    public List<CourseListResponseDTO> getRandomCourseByCategory(int categoryNum, int size) {
        CourseCategory category = Arrays.stream(CourseCategory.values())
                .filter(course -> course.getCategoryNumber() == categoryNum)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category number." + categoryNum));

        List<Course> randomCourses = courseRepository.findRandomCoursesBySize(category, size);

        return randomCourses.stream()
                .map(course -> CourseListResponseDTO.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .image(course.getImage() == null ? null : course.getImage().getPath())
                        .tutorName(course.getTutor().getUser().getName())
                        .build()
                ).toList();

    }

    // 수강신청할 코스 탐색
    @Transactional
    public Page<CourseListResponseDTO> getCourseByCategory(int categoryNum, int currentPage, int size) {
        Pageable pageable = PageRequest.of(currentPage - 1, size);
        CourseCategory category = Arrays.stream(CourseCategory.values())
                .filter(course -> course.getCategoryNumber() == categoryNum)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 카테고리를 찾을 수 없습니다. : " + categoryNum));
        if (CourseCategory.ALL.getCategoryNumber() == categoryNum) {
            return courseRepository.findAll(pageable)
                    .map(course -> CourseListResponseDTO.builder()
                            .id(course.getId())
                            .title(course.getTitle())
                            .image(course.getImage() == null ? null : course.getImage().getPath())
                            .tutorName(course.getTutor().getUser().getName())
                            .build());
        }
        Page<Course> page = courseRepository.findAllByCategory(category, pageable);
        List<CourseListResponseDTO> list = page.stream()
                .map(course -> CourseListResponseDTO.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .image(course.getImage().getPath())
                        .tutorName(course.getTutor().getUser().getName())
                        .build())
                .toList();
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }

    // 강사 대시보드리스트 조회
    @Transactional
    public List<TutorGetCourseListResponseDTO> getCurriculumList(String username) {
        Tutor tutor = tutorRepository.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        List<Course> courseList = courseRepository.findByTutor(tutor);
        List<TutorGetCourseListResponseDTO> tutorGetCourseList = new ArrayList<>();
        for (Course course : courseList) {
            tutorGetCourseList.add(TutorGetCourseListResponseDTO.builder()
                    .id(course.getId())
                    .title(course.getTitle())
                    .image(course.getImage().getPath())
                    .category(course.getCategory())
                    .build());
        }
        return tutorGetCourseList;
    }

    // 수강신청 요청
    @Transactional
    public CourseResponseDTO createCourseFormRegistration(
            User user, Long courseId, CourseRegistrationRequestDTO requestDTO
    ) {
        Affiliation affiliation = affiliationRepository.findByUser(user);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지않는 코스입니다."));
        CourseProvide courseProvide = CourseProvide.builder()
                .course(course)
                .beginDate(requestDTO.getBeginDate())
                .endDate(requestDTO.getEndDate())
                .attendeeCount(requestDTO.getAttendeeCount())
                .supervisor(affiliation)
                .build();
        courseProvideRepository.save(courseProvide);
        return CourseResponseDTO.builder()
                .msg("제출 되었습니다.")
                .build();
    }

    @Transactional
    public CourseResponseDTO updateCourseProvideState(
            User user, Long courseProvideId, CourseProvideStateRequestDTO requestDTO
    ) {
        Manager manager = managerRepository.findByUser(user);
        if (manager == null) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        CourseProvide courseProvide = courseProvideRepository.findById(courseProvideId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코스 입니다."));
        courseProvide.patch(requestDTO.getState());
        courseProvideRepository.save(courseProvide);
        return CourseResponseDTO.builder()
                .msg("상태 변경 완료")
                .build();
    }

    // 수강신청 요청 사항 조회
    @Transactional
    public CourseProvideListResponseDTO getCourseProvideRequestList(User user, Long courseProvideId) {
        CourseProvide findCourseProvide = courseProvideRepository.findById(courseProvideId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다."));
        User findSupervisor = userRepository.findById(findCourseProvide.getSupervisor().getId())
                .orElseThrow(() -> new IllegalArgumentException("교육담당자가 존재하지 않습니다."));
        List<CourseLearnerResponseDTO> learners = new ArrayList<>();
        List<Enrollment> findEnrollment = enrollmentRepository.findAllByCourseProvideId(findCourseProvide.getId());
        for (Enrollment enrollment : findEnrollment) {
            learners.add(
                    CourseLearnerResponseDTO.builder()
                            .department(enrollment.getUser().getAffiliation().getDepartment().getName())
                            .username(enrollment.getUser().getUsername())
                            .name(enrollment.getUser().getName())
                            .birth(enrollment.getUser().getBirth().toString())
                            .build()
            );
        }
        return CourseProvideListResponseDTO.builder()
                .beginDate(findCourseProvide.getBeginDate())
                .endDate(findCourseProvide.getEndDate())
                .state(findCourseProvide.getState())
                .attendeeCount(findCourseProvide.getAttendeeCount())
                .applicantName(findSupervisor.getName())
                .applicantPhone(findSupervisor.getPhone())
                .applicantEmail(findSupervisor.getEmail())
                .applicationDate(findCourseProvide.getCreatedAt().toLocalDate())
                .learners(learners)
                .build();
    }
}