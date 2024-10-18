package com.example.ahimmoyakbackend.enrollment.controller;


import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.enrollment.dto.*;
import com.example.ahimmoyakbackend.enrollment.service.EnrollmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("/enrollment")
    public ResponseEntity<List<EnrollmentGetListResponseDTO>> getEnrollmentList(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam(value = "companyId", required = false) Long companyId) {

        List<EnrollmentGetListResponseDTO> responseDTO = enrollmentService.getEnrollmentList(userDetails.getUser(), companyId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


    @PostMapping("/enrollment/registration") // 수강 신청  등록해주는거
    public ResponseEntity<EnrollmentClassRegistrationResponseDTO> registrationEnrollment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody EnrollmentClassRegistrationRequestDTO dto) {

        EnrollmentClassRegistrationResponseDTO responseDTO = enrollmentService.registration(dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);


    }

    @PatchMapping("/enrollment/cancel")
    public ResponseEntity<EnrollmentClassCancelResponseDTO> cancelEnrollment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long enrollmentId) {

        EnrollmentClassCancelResponseDTO responseDTO = enrollmentService.cancelEnrollment(enrollmentId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/enrollment/employee")
    public ResponseEntity<EnrollmentTossRosterResponseDTO> tossRoster(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody EnrollmentTossRosterRequestDTO dto) {

        EnrollmentTossRosterResponseDTO responseDTO = enrollmentService.tossRoster(userDetails.getUser(), dto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/enrollment/company")
    public ResponseEntity<List<EnrollmentReturnCompanyListResponseDTO>> returnCompanyList(@AuthenticationPrincipal UserDetailsImpl userDetails){

        List<EnrollmentReturnCompanyListResponseDTO> responseDTO = enrollmentService.returnCompanyList(userDetails.getUser());


        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


}
