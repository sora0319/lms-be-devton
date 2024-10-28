package com.example.ahimmoyakbackend.course.controller;

import com.example.ahimmoyakbackend.course.dto.ContentsCreateRequestDto;
import com.example.ahimmoyakbackend.course.service.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/course/{courseId}/curriculum/{curriculumId}/contents")
public class ContentsController {

    private final ContentsService contentsService;

    @PostMapping("/video")
    public ResponseEntity<String> addVideoContents(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("curriculumId") long curriculumId,
            @ModelAttribute ContentsCreateRequestDto requestDto
    ){
        return contentsService.addVideo(userDetails, curriculumId, requestDto) ? ResponseEntity.ok("비디오 등록 성공") : ResponseEntity.badRequest().body("비디오 등록 실패");
    }
}

