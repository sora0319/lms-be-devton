package com.example.ahimmoyakbackend.course.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.course.dto.*;
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

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDetailsInquiryResponseDTO> inquiryCourse(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long courseId) {

        CourseDetailsInquiryResponseDTO responseDTO = courseService.inquiry(userDetails.getUser(), courseId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    // 마이페이지 코스목록 조회
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

    @GetMapping("/preview")
    public ResponseEntity<List<CourseListResponseDTO>> getPreviewRandomCourseList(
            @RequestParam int categoryNum,
            @Positive @RequestParam @PageableDefault(value = 6) int size
    ) {
        List<CourseListResponseDTO> coursePage = courseService.getRandomCourseByCategory(categoryNum, size);
        return ResponseEntity.status(HttpStatus.OK).body(coursePage);
    }

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

    @PostMapping
    public ResponseEntity<CourseCreateResponseDTO> createCourse(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CourseCreateRequestDTO dto) {

        CourseCreateResponseDTO responseDTO = courseService.create(userDetails.getUser(), dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    // 수강신청 요청
    @PostMapping("/{courseId}/provide")
    public ResponseEntity<CourseResponseDTO> getCourseRegistration(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long courseId,
            @RequestBody CourseRegistrationRequestDTO requestDTO
    ) {
        CourseResponseDTO responseDTO = courseService.createCourseFormRegistration(userDetails.getUser(), courseId, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PatchMapping
    public ResponseEntity<CourseModifyResponseDTO> modifyCourse(@RequestParam Long courseId, @RequestBody CourseModifyRequestDTO dto) {

        CourseModifyResponseDTO responseDTO = courseService.modify(courseId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }

    @DeleteMapping
    public ResponseEntity<CourseDeleteResponseDTO> deleteCourse(@RequestParam Long courseId) {

        CourseDeleteResponseDTO responseDTO = courseService.delete(courseId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    // 수강신청 요청의 응답
    @PatchMapping("/{courseProvideId}/requests")
    public ResponseEntity<CourseResponseDTO> updateCourseProvideState(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long courseProvideId,
            @RequestBody CourseProvideStateRequestDTO requestDTO
    ) {
        CourseResponseDTO responseDTO = courseService.updateCourseProvideState(userDetails.getUser(), courseProvideId, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    // 수강신청 요청 사항 조회
    @GetMapping("/{courseProvideId}")
    public ResponseEntity<CourseProvideListResponseDTO> getCourseProvideRequestList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long courseProvideId
    ) {
        CourseProvideListResponseDTO response =
                courseService.getCourseProvideRequestList(userDetails.getUser(), courseProvideId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
