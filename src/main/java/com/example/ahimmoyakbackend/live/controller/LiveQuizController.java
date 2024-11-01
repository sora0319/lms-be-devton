package com.example.ahimmoyakbackend.live.controller;

import com.example.ahimmoyakbackend.live.dto.*;
import com.example.ahimmoyakbackend.live.service.LiveQuizService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/live/{liveId}/quiz")
public class LiveQuizController {

    private static final Logger log = LoggerFactory.getLogger(LiveQuizController.class);
    private final SimpMessageSendingOperations messagingTemplate;
    private final LiveQuizService liveQuizService;

    @PostMapping
    public ResponseEntity<String> createQuiz(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("liveId") Long liveId,
            @RequestBody LiveQuizCreateRequestDto requestDto
            ) {
        return liveQuizService.create(userDetails, liveId, requestDto)
                ? ResponseEntity.ok("퀴즈 생성 성공")
                : ResponseEntity.badRequest().body("퀴즈 생성 실패");
    }

    @GetMapping
    public ResponseEntity<List<LiveQuizResponseDto>> getQuiz(@PathVariable("liveId") Long liveId) {
        return ResponseEntity.ok(liveQuizService.getList(liveId));
    }

    @MessageMapping("/quiz/{liveId}")
    public ResponseEntity<LiveQuizResponseDto> sendQuiz(@DestinationVariable long liveId, QuizIdProxyDto dto) {
        LiveQuizResponseDto subDto = liveQuizService.send(liveId, dto.quizId());
        if (subDto == null) {
            return ResponseEntity.notFound().build();
        }
        messagingTemplate.convertAndSend("/sub/quiz/" + liveId, subDto);
        return ResponseEntity.ok(subDto);
    }

    @MessageMapping("/quiz/{liveId}/answer")
    public ResponseEntity<List<QuizStatusSubDto>> answerQuiz(@DestinationVariable long liveId, QuizAnswerMsgDto pubDto) {
        List<QuizStatusSubDto> subDto = liveQuizService.answer(liveId, pubDto);
        if (subDto == null) {
            return ResponseEntity.notFound().build();
        }
        messagingTemplate.convertAndSend("/sub/quiz/" + liveId + "/answer", subDto);
        return ResponseEntity.ok(subDto);
    }
}
