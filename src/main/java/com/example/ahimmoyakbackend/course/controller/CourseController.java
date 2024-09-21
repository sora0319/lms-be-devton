package com.example.ahimmoyakbackend.course.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.course.dto.*;
import com.example.ahimmoyakbackend.course.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<CourseDetailsInquiryResponseDTO> inquiryCourse(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long courseId) {

        CourseDetailsInquiryResponseDTO responseDTO = courseService.inquiry(userDetails.getUser(), courseId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }

    @PostMapping("/course")
    public ResponseEntity<CourseCreateResponseDTO> createCourse(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CourseCreateRequestDTO dto) {

        CourseCreateResponseDTO responseDTO = courseService.create(dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    ;

    @PatchMapping("/update")
    public ResponseEntity<CourseModifyResponseDTO> modifyCourse(@RequestParam Long courseId, CourseModifyRequestDTO dto) {

        CourseModifyResponseDTO responseDTO = courseService.modify(courseId, dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<CourseDeleteResponseDTO> deleteCourse(@RequestParam Long courseId) {

        CourseDeleteResponseDTO responseDTO = courseService.delete(courseId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }
}
