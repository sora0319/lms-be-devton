package com.example.ahimmoyakbackend.curriculum.service;

import com.example.ahimmoyakbackend.auth.common.UserRole;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.company.repository.AffiliationRepository;
import com.example.ahimmoyakbackend.company.repository.CourseProvideRepository;
import com.example.ahimmoyakbackend.course.dto.CurriculumCreateRequestDTO;
import com.example.ahimmoyakbackend.course.dto.CurriculumResponseDTO;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import com.example.ahimmoyakbackend.course.repository.ContentsHistoryRepository;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.course.repository.CurriculumRepository;
import com.example.ahimmoyakbackend.course.repository.EnrollmentRepository;
import com.example.ahimmoyakbackend.curriculum.dto.CurriculumChangeOrderResponseDTO;
import com.example.ahimmoyakbackend.institution.repository.ManagerRepository;
import com.example.ahimmoyakbackend.institution.repository.TutorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurriculumService {

    private final CurriculumRepository curriculumRepository;
    private final CourseRepository courseRepository;
    private final ContentsHistoryRepository contentsHistoryRepository;
    private final CourseProvideRepository courseProvideRepository;
    private final TutorRepository tutorRepository;
    private final AffiliationRepository affiliationRepository;
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    // 커리큘럼 생성
    @Transactional
    public void create(User user, Long courseId, CurriculumCreateRequestDTO requestDTO) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코스입니다."));

        if (user.getRole() != UserRole.MANAGER) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Curriculum curriculum = Curriculum.builder()
                .course(course)
                .title(requestDTO.getTitle())
                .idx(requestDTO.getIdx())
                .build();

        curriculumRepository.save(curriculum);
    }

    // 커리큘럼 제거
    @Transactional
    public CurriculumResponseDTO delete(User user, Long courseId, Long curriculumId) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코스입니다."));

        if (user.getRole() != UserRole.MANAGER) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Curriculum curriculum = curriculumRepository.findById(curriculumId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 커리큘럼입니다."));

        curriculumRepository.delete(curriculum);

        return CurriculumResponseDTO.builder()
                .msg("삭제되었습니다.")
                .build();
    }

    // 커리큘럼 수정
    @Transactional
    public CurriculumResponseDTO modifyCurriculum(User user, Long courseId, Long curriculumId, CurriculumCreateRequestDTO requestDTO) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코스입니다."));

        if (user.getRole() != UserRole.MANAGER) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Curriculum curriculum = curriculumRepository.findById(curriculumId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 커리큘럼입니다."));

        curriculum.patch(requestDTO.getTitle(), requestDTO.getIdx());

        curriculumRepository.save(curriculum);

        return CurriculumResponseDTO.builder()
                .msg("수정되었습니다.")
                .build();
    }

    public CurriculumChangeOrderResponseDTO changeOrder(Long courseId, Long target1, Long target2) throws IllegalArgumentException {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("코스가 없습니다,"));

        Curriculum curriculum1 = curriculumRepository.findById(target1).orElseThrow(() -> new IllegalArgumentException("선택한 커리큘럼이 없습니다."));
        Curriculum curriculum2 = curriculumRepository.findById(target2).orElseThrow(() -> new IllegalArgumentException("바꿀 커리큘럼이 없습니다."));

        if (!(course.getId().equals(curriculum1.getCourse().getId()) || course.getId().equals(curriculum2.getCourse().getId())))
            throw new IllegalArgumentException("코스에 맞는 커리큘럼이 아닙니다.");
        Curriculum changeIdx1 = Curriculum.builder()
                .id(curriculum1.getId())
                .title(curriculum1.getTitle())
                .idx(curriculum2.getIdx())
                .build();

        Curriculum changeIdx2 = Curriculum.builder()
                .id(curriculum2.getId())
                .title(curriculum2.getTitle())
                .idx(curriculum1.getIdx())
                .build();

        curriculumRepository.save(changeIdx1);
        curriculumRepository.save(changeIdx2);

        return CurriculumChangeOrderResponseDTO.builder()
                .msg("성공적으로 idx 변경됨.")
                .build();


    }
}
