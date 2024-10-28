package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.course.common.CourseState;
import com.example.ahimmoyakbackend.course.dto.*;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.ContentsRepository;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.course.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final UserService userService;
    private final CourseRepository courseRepository;

    @Override
    public CourseDetailResponseDto getDetail(long id) {
        return courseRepository.findById(id)
                .map(course -> CourseDetailResponseDto
                        .from(course, course.getCurriculumList().stream()
                                .map(curriculum -> CurriculumListResponseDto
                                        .from(curriculum, curriculum.getContentsList().stream()
                                                .map(ContentListResponseDto::from
                                                ).toList())
                                ).toList())).orElse(null);
    }

    @Override
    @Transactional
    public boolean create(UserDetails userDetails, CourseCreateRequestDto requestDto) {
        courseRepository.save(requestDto.toEntity(userService.getAuth(userDetails)));
        return true;
    }

    @Override
    @Transactional
    public boolean update(UserDetails userDetails, long id, CourseCreateRequestDto requestDto) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null || course.getTutor().equals(userService.getAuth(userDetails))) {
            return false;
        }
        courseRepository.save(course.patch(requestDto));
        return true;
    }

    @Override
    @Transactional
    public boolean delete(UserDetails userDetails, long id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null || course.getTutor().equals(userService.getAuth(userDetails))) {
            return false;
        }
        courseRepository.save(course.setState(CourseState.REMOVED));
        return true;
    }

    @Override
    public List<CourseListResponseDto> getList(UserDetails userDetails) {
        return courseRepository.findByEnrollments_User(userService.getAuth(userDetails)).stream()
                .map(CourseListResponseDto::from)
                .toList();
    }
}
