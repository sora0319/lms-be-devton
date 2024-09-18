package com.example.ahimmoyakbackend.course.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.course.common.ContentsHistoryState;
import com.example.ahimmoyakbackend.course.dto.CourseDetailsInquiryResponseDTO;
import com.example.ahimmoyakbackend.course.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<CourseDetailsInquiryResponseDTO> InquiryCourse(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long courseId) {


        CourseDetailsInquiryResponseDTO responseDTO = courseService.Inquiry(userDetails.getUser(), courseId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }
}
