package com.example.ahimmoyakbackend.course.controller;

import com.example.ahimmoyakbackend.course.dto.ContentsCreateRequestDto;
import com.example.ahimmoyakbackend.course.dto.ContentsInfoResponseDto;
import com.example.ahimmoyakbackend.course.service.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course/{courseId}/curriculum/{curriculumId}/contents")
public class ContentsController {

    private final ContentsService contentsService;

    @PostMapping
    public ResponseEntity<String> addContents(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("curriculumId") long curriculumId,
            @ModelAttribute ContentsCreateRequestDto requestDto
    ){
        return contentsService.add(userDetails, curriculumId, requestDto) ? ResponseEntity.ok("콘텐츠 등록 성공") : ResponseEntity.badRequest().body("콘텐츠 등록 실패");
    }

    @GetMapping("/{contentsId}")
    public ResponseEntity<ContentsInfoResponseDto> getContentsInfo(@PathVariable("contentsId") long contentsId) {
        return ResponseEntity.ok(contentsService.getInfo(contentsId));
    }
}

