package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.dto.CourseListResponseDTO;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    // 유저 마이페이지에서 코스리스트 조회
    @Transactional
    public Page<CourseListResponseDTO> findUserCourseList(Long userId, Long institutionId, int page, @Positive int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (userId == null || institutionId == null) {
            return null;
        }
        Page<Course> coursePage = courseRepository.findAll(pageable);
        List<CourseListResponseDTO> list = coursePage.stream()
                .map(course -> CourseListResponseDTO.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .image(course.getImage())
                        .build())
                .toList();
        return new PageImpl<>(list, coursePage.getPageable(), coursePage.getTotalElements());
    }

    // 메인페이지에서 코스리스트 조회
    public Page<CourseListResponseDTO> getCourseByCategory(int categoryNum, int currentPage, int size) {
        Pageable pageable = PageRequest.of(currentPage - 1, size);
        CourseCategory category = Arrays.stream(CourseCategory.values())
                .filter(course -> course.getCategoryNumber() == categoryNum).findFirst().get();

        if (CourseCategory.ALL.getCategoryNumber() == categoryNum) {
            return courseRepository.findAll(pageable)
                    .map(course -> CourseListResponseDTO.builder()
                            .id(course.getId())
                            .title(course.getTitle())
                            .image(course.getImage())
                            .tutor(course.getTutor())
                            .build());
        }
        Page<Course> page = courseRepository.findAllByCategory(category, pageable);

        List<CourseListResponseDTO> list = page.stream()
                .map(course -> CourseListResponseDTO.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .image(course.getImage())
                        .tutor(course.getTutor())
                        .build())
                .toList();
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }
}