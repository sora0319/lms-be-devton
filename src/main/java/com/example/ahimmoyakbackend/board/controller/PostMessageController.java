package com.example.ahimmoyakbackend.board.controller;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.dto.SendPostMessageRequestDto;
import com.example.ahimmoyakbackend.board.dto.SendPostMessageResponseDto;
import com.example.ahimmoyakbackend.board.dto.ReceivePostMessageResponseDto;
import com.example.ahimmoyakbackend.board.service.PostMessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PostMessageController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostMessageController {

    private final PostMessageService postMessageService;

    @PostMapping("/v1/message")
    public ResponseEntity<SendPostMessageResponseDto> sendMessage(@RequestBody SendPostMessageRequestDto messageRequestDto) {
        SendPostMessageResponseDto sendMessage = postMessageService.write(messageRequestDto);
      return ResponseEntity.status(HttpStatus.OK).body(sendMessage);
    }

    @GetMapping("/v1/message/send")
    public ResponseEntity<ReceivePostMessageResponseDto> receiveInquiry(@AuthenticationPrincipal User user, @RequestParam(defaultValue = "1") int page,
                                                                        @RequestParam(defaultValue = "10") int size){
        ReceivePostMessageResponseDto receiveMessage = postMessageService.receiveInquriy(user,page,size);
        return ResponseEntity.status(HttpStatus.OK).body(receiveMessage);
    }
}
