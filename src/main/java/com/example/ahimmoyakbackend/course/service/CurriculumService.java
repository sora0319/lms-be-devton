package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.auth.common.UserRole;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.course.dto.CurriculumCreateRequestDTO;
import com.example.ahimmoyakbackend.course.dto.CurriculumResponseDTO;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.course.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurriculumService {
    private final CurriculumRepository curriculumRepository;
    private final CourseRepository courseRepository;

    // 커리큘럼 생성
    @Transactional
    public void create(User user, Long courseId, CurriculumCreateRequestDTO requestDTO) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코스입니다."));

        if (user.getRole() != UserRole.MANAGE) {
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

        if (user.getRole() != UserRole.MANAGE) {
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

        if (user.getRole() != UserRole.MANAGE) {
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
}