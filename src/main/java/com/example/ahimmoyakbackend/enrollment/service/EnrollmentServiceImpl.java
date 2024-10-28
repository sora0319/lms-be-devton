package com.example.ahimmoyakbackend.enrollment.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.course.common.EnrollmentState;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.entity.Enrollment;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.course.repository.EnrollmentRepository;
import com.example.ahimmoyakbackend.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final UserService userService;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public boolean make(UserDetails userDetails, long courseId) {
        User user = userService.getAuth(userDetails);
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "코스를 찾을 수 없습니다.");
        }
        if(course.getBeginDate().isBefore(LocalDate.now())){
            throw new ApiException(HttpStatus.METHOD_NOT_ALLOWED, "이미 시작된 코스입니다.");
        }
        enrollmentRepository.save(Enrollment.builder()
                .user(user)
                .course(course)
                .state(EnrollmentState.NOT_STARTED)
                .build());
        return true;
    }

    @Override
    @Transactional
    public boolean cancel(UserDetails userDetails, long enrollId) {
        User user = userService.getAuth(userDetails);
        Enrollment enrollment = enrollmentRepository.findById(enrollId).orElse(null);
        if(enrollment == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "수강신청을 찾을 수 없습니다.");
        }
        if(enrollment.getCourse().getBeginDate().isBefore(LocalDate.now())){
            throw new ApiException(HttpStatus.METHOD_NOT_ALLOWED, "이미 시작된 코스입니다.");
        }
        enrollmentRepository.delete(enrollment);
        return true;
    }

    @Override
    @Transactional
    public boolean cancel(long courseId, UserDetails userDetails) {
        User user = userService.getAuth(userDetails);
        Course course = courseRepository.findById(courseId).orElse(null);
        if(course == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "코스를 찾을 수 없습니다.");
        }
        Enrollment enrollment = enrollmentRepository.findByUserAndCourse(user, course).orElse(null);
        if(enrollment == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "수강신청을 찾을 수 없습니다.");
        }
        if(course.getBeginDate().isBefore(LocalDate.now())){
            throw new ApiException(HttpStatus.METHOD_NOT_ALLOWED, "이미 시작된 코스입니다.");
        }
        enrollmentRepository.delete(enrollment);
        return true;
    }
}
