package com.example.ahimmoyakbackend.auth.controller;

import com.example.ahimmoyakbackend.auth.dto.*;
import com.example.ahimmoyakbackend.auth.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginRequestDTO requestDto, HttpServletResponse response) {
        UserLoginResponseDTO responseDto = userService.login(requestDto, response);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> checkRefreshToken( HttpServletResponse response) {
        UserReissueResponseDTO responseDto = userService.reissue( response);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/v1/manager/test")
    public ResponseEntity<Object> test() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}