package com.example.ahimmoyakbackend.course.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.course.dto.CourseListResponseDTO;
import com.example.ahimmoyakbackend.course.dto.CourseRegistrationRequestDTO;
import com.example.ahimmoyakbackend.course.dto.CourseResponseDTO;
import com.example.ahimmoyakbackend.course.dto.TutorGetCourseListResponseDTO;
import com.example.ahimmoyakbackend.course.service.CourseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CourseController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
public class CourseController {
    private final CourseService courseService;

    // 마이페이지 코스리스트 조회
    @GetMapping("/myPage")
    public ResponseEntity<Page<CourseListResponseDTO>> getCourseListMyPage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long institutionId,
            @Positive @RequestParam @PageableDefault(value = 1) int page,
            @Positive @RequestParam @PageableDefault(value = 6) int size
    ) {
        Page<CourseListResponseDTO> pageCourse = courseService.findUserCourseList(userDetails.getUser(), institutionId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(pageCourse);
    }

    // 수강신청할 코스 탐색
    @GetMapping("/main")
    public ResponseEntity<Page<CourseListResponseDTO>> getCourseListMainPage(
            @RequestParam int categoryNum,
            @Positive @RequestParam @PageableDefault(value = 1) int page,
            @Positive @RequestParam @PageableDefault(value = 6) int size
    ) {
        Page<CourseListResponseDTO> coursePage = courseService.getCourseByCategory(categoryNum, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(coursePage);
    }

    // 강사 대시보드리스트 조회
    @GetMapping
    public ResponseEntity<List<TutorGetCourseListResponseDTO>> getCurriculumList(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        String username = userDetails.getUsername();
        List<TutorGetCourseListResponseDTO> responseDto = courseService.getCurriculumList(username);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 수강신청 요청
    @PostMapping("/{courseId}/provide")
    public ResponseEntity<CourseResponseDTO> getCourseRegistration(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long courseId,
            @RequestBody CourseRegistrationRequestDTO requestDTO
    ) {
        courseService.createCourseRegistration(userDetails.getUser(), courseId, requestDTO);
        CourseResponseDTO responseDTO = CourseResponseDTO.builder()
                .msg("수강신청이 완료되었습니다.")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}