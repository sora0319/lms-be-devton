package com.example.ahimmoyakbackend.auth.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.dto.*;
import com.example.ahimmoyakbackend.auth.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserController")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserJoinResponseDTO> join(@RequestBody @Valid UserJoinRequestDTO requestDto) {
        UserJoinResponseDTO created = userService.create(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @PostMapping("/join/employee")
    public ResponseEntity<EmployeeJoinResponseDTO> joinEmployee(@RequestBody EmployeeJoinRequestDTO requestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        EmployeeJoinResponseDTO responseDTO = userService.register(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO requestDto, HttpServletResponse response) {
        UserLoginResponseDTO responseDto = userService.login(requestDto, response);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/delete")
    public ResponseEntity<UserDeleteResponseDTO> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserDeleteResponseDTO responseDto = userService.delete(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<UserReissueResponseDTO> checkRefreshToken() {
        UserReissueResponseDTO responseDto = userService.reissue();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/exist/name")
    public ResponseEntity<ExistNameResponseDTO> checkExistName(@RequestBody ExistNameRequestDTO requestDTO){
        ExistNameResponseDTO responseDTO = userService.checkExistName(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/user/verification")
    public ResponseEntity<UserVerificationResponseDTO> checkVerification(@RequestBody UserVerificationRequestDTO requestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserVerificationResponseDTO responseDTO = userService.checkVerification(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/user/update")
    public ResponseEntity<UserInformationResponseDTO> updatePersonalInformation(@RequestBody UserInformationRequestDTO requestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserInformationResponseDTO responseDTO = userService.updatePersonalInformation(requestDTO, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/v1/manager/test")
    public ResponseEntity<Object> test() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}