package com.example.ahimmoyakbackend.board.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.dto.*;
import com.example.ahimmoyakbackend.board.service.CourseBoardService;
import com.example.ahimmoyakbackend.board.service.CourseCommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CourseBoardController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseBoardController {

    private final CourseBoardService courseBoardService;
    private final CourseCommentService courseCommentService;
    private final UserService userService;

    @PostMapping("/v1/courseBoard/{type}/{courseId}")
    public ResponseEntity<BoardCreateResponseDto> createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BoardCreateRequestDto requestDTO, @PathVariable Long courseId, @PathVariable BoardType type){
        BoardCreateResponseDto created = courseBoardService.create(userService.getAuth(userDetails),requestDTO,courseId,type);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @PatchMapping("/v1/courseBoard/{courseId}")
    public ResponseEntity<BoardUpdateResponseDto> updateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BoardUpdateRequestDto requestDTO, @PathVariable Long courseId, @RequestParam Long courseBoardId){
        BoardUpdateResponseDto updated = courseBoardService.update(userDetails.getUser(),requestDTO,courseId,courseBoardId);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/v1/courseBoard/{courseId}")
    public ResponseEntity<BoardDeleteResponseDto> deleteBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long courseId, @RequestParam Long courseBoardId){
        BoardDeleteResponseDto deleted = courseBoardService.delete(userDetails.getUser(),courseId,courseBoardId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

    @GetMapping("/v1/courseBoard/{courseId}")
    public ResponseEntity<CourseBoardInquiryResponseDto> inquiryBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long courseId,
                                                                      @RequestParam BoardType type,
                                                                      @RequestParam(defaultValue = "1") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        CourseBoardInquiryResponseDto responseDto = courseBoardService.inquiry(userDetails.getUser(),courseId, type, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    @GetMapping("/v1/courseBoard/myBoard/{courseId}")
    public ResponseEntity<CourseBoardInquiryResponseDto> inquiryBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long courseId,
                                                                      @RequestParam(defaultValue = "1") int page,
                                                                      @RequestParam(defaultValue = "10") int size) {
        CourseBoardInquiryResponseDto responseDto = courseBoardService.inquiryCreatedBoard(userDetails.getUser(),courseId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/v1/courseBoard/{courseId}/{type}")
    public ResponseEntity<CourseBoardShowResponseDto> showBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long courseId,  @PathVariable BoardType type, @RequestParam Long courseBoardId){
        CourseBoardShowResponseDto board = courseBoardService.show(userDetails.getUser(),courseId,type,courseBoardId);
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

    @PostMapping("/v1/courseBoard/comment/{courseBoardId}")
    public ResponseEntity<CommentCreateResponseDto> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody CommentCreateRequestDto requestDto,@PathVariable Long courseBoardId){
        CommentCreateResponseDto comment = courseCommentService.create(userDetails.getUser(),requestDto,courseBoardId);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @DeleteMapping("/v1/courseBoard/comment/{courseBoardId}")
    public ResponseEntity<CommentCreateResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long courseBoardId,@RequestParam Long commentId){
        CommentCreateResponseDto comment = courseCommentService.deleteComment(userDetails.getUser(),courseBoardId,commentId);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

}
