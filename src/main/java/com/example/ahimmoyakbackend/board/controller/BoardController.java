package com.example.ahimmoyakbackend.board.controller;

import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.dto.*;
import com.example.ahimmoyakbackend.board.service.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "BoardController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/v1/board/{type}")
    public ResponseEntity<BoardCreateResponseDTO> createBoard(@RequestBody BoardCreateRequestDTO requestDTO, @PathVariable BoardType type){
        BoardCreateResponseDTO created = boardService.create(requestDTO,type);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @PatchMapping("/v1/board")
    public ResponseEntity<BoardUpdateResponseDTO> updateBoard(@RequestBody BoardUpdateRequestDTO requestDTO, @RequestParam Long boardId){
        BoardUpdateResponseDTO updated = boardService.update(requestDTO,boardId);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/v1/board")
    public ResponseEntity<BoardDeleteResponseDTO> deleteBoard(@RequestParam Long boardId){
        BoardDeleteResponseDTO deleted = boardService.delete(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

    @GetMapping("/v1/board")
    public ResponseEntity<Page<BoardInquiryResponseDTO>> inquiryBoard(@RequestParam BoardType type,
                                                                      @RequestParam(defaultValue = "1") int page,
                                                                      @RequestParam(defaultValue = "10") int size){
        Page<BoardInquiryResponseDTO> boardPage = boardService.inquiry(type,page,size);
        return ResponseEntity.status(HttpStatus.OK).body(boardPage);
    }

    @GetMapping("/v1/board/{type}")
    public ResponseEntity<BoardShowResponseDTO> showBoard(@PathVariable BoardType type, @RequestParam Long boardId){
        BoardShowResponseDTO board = boardService.show(type,boardId);
        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

}
