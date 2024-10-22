package com.example.ahimmoyakbackend.live.controller;

import com.example.ahimmoyakbackend.live.dto.LiveCourseResponseDTO;
import com.example.ahimmoyakbackend.live.dto.LiveCreateRequestDTO;
import com.example.ahimmoyakbackend.live.dto.LivePublishFormDTO;
import com.example.ahimmoyakbackend.live.dto.LiveTutorResponseDTO;
import com.example.ahimmoyakbackend.live.service.LiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/live")
@RequiredArgsConstructor
public class LiveController {

    private final LiveService liveService;

    @PostMapping
    public ResponseEntity<Void> createLive(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long courseProvideId, @RequestBody LiveCreateRequestDTO requestDTO) {
        boolean result = liveService.createLive(requestDTO, courseProvideId, userDetails.getUsername());
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<LiveCourseResponseDTO>> getCourseLives(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long courseProvideId) {
        List<LiveCourseResponseDTO> liveList = liveService.getLiveListByCourse(courseProvideId);
        return ResponseEntity.ok().body(liveList);
    }

    @GetMapping("/instructor")
    public ResponseEntity<List<LiveTutorResponseDTO>> getTutorLives(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long instructorId) {
        List<LiveTutorResponseDTO> liveList = liveService.getLiveListByTutor(instructorId);
        return ResponseEntity.ok().body(liveList);
    }

    @PostMapping("/publish")
    public ResponseEntity<Void> publishLive(@ModelAttribute LivePublishFormDTO form) {
        boolean valid = liveService.publishLive(form.getName());
        return valid ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/publish-done")
    public ResponseEntity<Void> publishLiveDone(@ModelAttribute LivePublishFormDTO form) {
        liveService.endLive(form.getName());
        return ResponseEntity.ok().build();
    }
}