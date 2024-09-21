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
    public ResponseEntity<PostMessageInquiryResponseDto> sendInquiry(@RequestParam Long id,@AuthenticationPrincipal User user,
                                                                     @RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int size){
        PostMessageInquiryResponseDto receiveMessage = postMessageService.sendInquriy(id,user,page,size);
        return ResponseEntity.status(HttpStatus.OK).body(receiveMessage);
    }

    @GetMapping("/v1/message/receive")
    public ResponseEntity<ReceivePostMessageInquiryResponseDto> receiveInquiry(@RequestParam Long id,@AuthenticationPrincipal User user,
                                                                        @RequestParam(defaultValue = "1") int page,
                                                                        @RequestParam(defaultValue = "10") int size){
        ReceivePostMessageInquiryResponseDto receiveMessage = postMessageService.receiveInquriy(id,user,page,size);
        return ResponseEntity.status(HttpStatus.OK).body(receiveMessage);
    }

    @GetMapping("/v1/message/send/{id}/{message}")
    public ResponseEntity<PostMessageResponseDto> showSendMessage(@PathVariable("id") Long id,@AuthenticationPrincipal User user,@PathVariable("message")Long messageId){
        PostMessageResponseDto message = postMessageService.showSendMessage(id,user,messageId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/v1/message/receive/{id}/{message}")
    public ResponseEntity<PostMessageShowResponseDto> showReceiveMessage(@PathVariable("id")  Long id,@AuthenticationPrincipal User user,@PathVariable("message") Long messageId){
        PostMessageShowResponseDto message = postMessageService.showReceiveMessage(id,user,messageId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @DeleteMapping("/v1/message")
    public ResponseEntity<DeletePostMessageResponseDto> deleteMessage(@RequestParam Long messageId){
        DeletePostMessageResponseDto message = postMessageService.deleteMessage(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
