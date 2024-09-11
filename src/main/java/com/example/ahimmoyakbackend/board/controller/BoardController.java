package com.example.ahimmoyakbackend.board.controller;

import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.dto.*;
import com.example.ahimmoyakbackend.board.service.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "BoardController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/v1/board/{boardType}")
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
    public ResponseEntity<List<BoardResponseDTO>> showBoard(@RequestParam BoardType type){
        List<BoardResponseDTO> boards = boardService.show(type);
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }
}
