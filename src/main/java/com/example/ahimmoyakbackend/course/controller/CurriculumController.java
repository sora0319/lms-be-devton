package com.example.ahimmoyakbackend.course.controller;

import com.example.ahimmoyakbackend.course.dto.CurriculumCreateRequestDto;
import com.example.ahimmoyakbackend.course.service.CurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course/{courseId}")
public class CurriculumController {

    private final CurriculumService curriculumService;

    @PostMapping("/curriculum")
    public ResponseEntity<String> addCurriculum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("courseId") long courseId,
            @RequestBody CurriculumCreateRequestDto requestDto
            ) {
        return curriculumService.add(userDetails, courseId, requestDto.title()) ? ResponseEntity.ok("커리큘럼 등록 성공") : ResponseEntity.badRequest().body("커리큘럼 등록 실패");
    }

    @PatchMapping("/curriculum/{curriculumId}")
    public ResponseEntity<String> updateCurriculum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("curriculumId") long curriculumId,
            @RequestBody CurriculumCreateRequestDto requestDto
            ) {
        return curriculumService.update(userDetails, curriculumId, requestDto.title()) ? ResponseEntity.ok("커리큘럼 수정 성공") : ResponseEntity.badRequest().body("커리큘럼 수정 실패");
    }

    @DeleteMapping("/curriculum/{curriculumId}")
    public ResponseEntity<String> deleteCurriculum(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("curriculumId") long curriculumId
    ) {
        return curriculumService.delete(userDetails, curriculumId) ? ResponseEntity.ok("커리큘럼 삭제 성공") : ResponseEntity.badRequest().body("커리큘럼 삭제 실패");
    }
}
