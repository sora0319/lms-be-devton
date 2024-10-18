package com.example.ahimmoyakbackend.curriculum.service;

import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.course.repository.CurriculumRepository;
import com.example.ahimmoyakbackend.curriculum.dto.CurriculumChangeOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurriculumService {

    private final CurriculumRepository curriculumRepository;
    private final CourseRepository courseRepository;

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
