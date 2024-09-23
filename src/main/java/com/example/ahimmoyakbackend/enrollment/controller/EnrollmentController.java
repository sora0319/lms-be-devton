package com.example.ahimmoyakbackend.enrollment.controller;


import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.enrollment.dto.EnrollmentGetListResponseDTO;
import com.example.ahimmoyakbackend.enrollment.service.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("/enrollment")
    public ResponseEntity<List<EnrollmentGetListResponseDTO>> getEnrollmentList(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long institutionId, @RequestParam Long companyId) {

        List<EnrollmentGetListResponseDTO> responseDTO = enrollmentService.getEnrollmentList(userDetails.getUser(),institutionId, companyId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
