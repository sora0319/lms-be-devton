package com.example.ahimmoyakbackend.board.controller;

import com.example.ahimmoyakbackend.board.dto.PostMessageRequestDto;
import com.example.ahimmoyakbackend.board.dto.PostMessageResponseDto;
import com.example.ahimmoyakbackend.board.service.PostMessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PostMessageController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostMessageController {

    private final PostMessageService postMessageService;

    @PostMapping("/v1/message")
    public ResponseEntity<PostMessageResponseDto> sendMessage(@RequestBody PostMessageRequestDto messageRequestDto) {
        PostMessageResponseDto sendMessage = postMessageService.write(messageRequestDto);
      return ResponseEntity.status(HttpStatus.OK).body(sendMessage);
    }
}
