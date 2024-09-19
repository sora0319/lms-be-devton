package com.example.ahimmoyakbackend.course.controller;

import com.example.ahimmoyakbackend.course.dto.CourseListResponseDTO;
import com.example.ahimmoyakbackend.course.service.CourseService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
public class CourseController {
    private final CourseService courseService;

    // 유저 마이페이지에서 코스리스트 조회
    @GetMapping("/myPage")
    public ResponseEntity<Page<CourseListResponseDTO>> getCourseListMyPage(
            @RequestParam Long userId,
            @RequestParam Long institutionId,
            @Positive @RequestParam @PageableDefault(value = 1) int page,
            @Positive @RequestParam @PageableDefault(value = 6) int size
    ) {
        Page<CourseListResponseDTO> pageCourse = courseService.findUserCourseList(userId, institutionId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(pageCourse);
    }

    // 메인페이지에서 코스리스트 조회
    @GetMapping("/main")
    public ResponseEntity<Page<CourseListResponseDTO>> getCourseListMainPage(
            @RequestParam int categoryNum,
            @Positive @RequestParam @PageableDefault(value = 1) int page,
            @Positive @RequestParam @PageableDefault(value = 6) int size
    ) {
        Page<CourseListResponseDTO> coursePage = courseService.getCourseByCategory(categoryNum, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(coursePage);
    }

    // 커리큘럼 리스트 조회

}