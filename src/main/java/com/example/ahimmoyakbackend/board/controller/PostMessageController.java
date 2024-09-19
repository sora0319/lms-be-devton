package com.example.ahimmoyakbackend.board.controller;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.dto.*;
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
    public ResponseEntity<PostMessageInquiryResponseDto> sendInquiry(@AuthenticationPrincipal User user,
                                                                     @RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int size){
        PostMessageInquiryResponseDto receiveMessage = postMessageService.sendInquriy(user,page,size);
        return ResponseEntity.status(HttpStatus.OK).body(receiveMessage);
    }

    @GetMapping("/v1/message/receive")
    public ResponseEntity<PostMessageInquiryResponseDto> receiveInquiry(@AuthenticationPrincipal User user,
                                                                        @RequestParam(defaultValue = "1") int page,
                                                                        @RequestParam(defaultValue = "10") int size){
        PostMessageInquiryResponseDto receiveMessage = postMessageService.receiveInquriy(user,page,size);
        return ResponseEntity.status(HttpStatus.OK).body(receiveMessage);
    }

    @GetMapping("/v1/message")
    public ResponseEntity<PostMessageResponseDto> showMessage(@RequestParam Long messageId){
        PostMessageResponseDto message = postMessageService.showMessage(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @DeleteMapping("/v1/message")
    public ResponseEntity<DeletePostMessageResponseDto> deleteMessage(@RequestParam Long messageId){
        DeletePostMessageResponseDto message = postMessageService.deleteMessage(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
