package com.example.ahimmoyakbackend.board.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.dto.*;
import com.example.ahimmoyakbackend.board.service.BoardService;
import com.example.ahimmoyakbackend.board.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "BoardController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping("/v1/board/{type}")
    public ResponseEntity<BoardCreateResponseDto> createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BoardCreateRequestDto requestDTO, @PathVariable BoardType type) {
        BoardCreateResponseDto created = boardService.create(userDetails.getUser(),requestDTO, type);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @PatchMapping("/v1/board")
    public ResponseEntity<BoardUpdateResponseDto> updateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BoardUpdateRequestDto requestDTO, @RequestParam Long boardId) {
        BoardUpdateResponseDto updated = boardService.update(userDetails.getUser(),requestDTO, boardId);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/v1/board")
    public ResponseEntity<BoardDeleteResponseDto> deleteBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long boardId) {
        BoardDeleteResponseDto deleted = boardService.delete(userDetails.getUser(),boardId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

    @GetMapping("/v1/board")
    public ResponseEntity<Page<BoardInquiryResponseDto>> inquiryBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam BoardType type,
                                                                 @RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Page<BoardInquiryResponseDto> boardPage = boardService.inquiry(type, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(boardPage);
    }

    @GetMapping("/v1/board/myBoard")
    public ResponseEntity<Page<BoardInquiryResponseDto>> inquiryCreatedBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                             @RequestParam(defaultValue = "1") int page,
                                                                             @RequestParam(defaultValue = "10") int size) {
        Page<BoardInquiryResponseDto> boardPage = boardService.inquiryCreatedBoard(userDetails.getUser(), page, size);
        return ResponseEntity.status(HttpStatus.OK).body(boardPage);
    }

    @GetMapping("/v1/board/{type}")
    public ResponseEntity<BoardShowResponseDto> showBoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable BoardType type, @RequestParam Long boardId) {
        BoardShowResponseDto board = boardService.show(type, boardId);
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

    @PostMapping("/v1/board/comment/{boardId}")
    public ResponseEntity<CommentCreateResponseDto> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentCreateRequestDto requestDto, @PathVariable Long boardId) {
        CommentCreateResponseDto comment = commentService.create(requestDto, boardId);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @DeleteMapping("/v1/board/comment/{boardId}")
    public ResponseEntity<CommentCreateResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId, @RequestParam Long commentId) {
        CommentCreateResponseDto comment = commentService.deleteComment(userDetails.getUser(),boardId,commentId);
        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }
}
