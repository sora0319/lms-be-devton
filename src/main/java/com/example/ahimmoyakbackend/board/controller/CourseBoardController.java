package com.example.ahimmoyakbackend.board.controller;

import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.dto.*;
import com.example.ahimmoyakbackend.board.service.CourseBoardService;
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

    @PostMapping("/v1/board/{type}/{courseId}")
    public ResponseEntity<BoardCreateResponseDTO> createBoard(@RequestBody BoardCreateRequestDTO requestDTO, @PathVariable Long courseId,@PathVariable BoardType type){
        BoardCreateResponseDTO created = courseBoardService.create(requestDTO,courseId,type);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @PatchMapping("/v1/board/{courseId}")
    public ResponseEntity<BoardUpdateResponseDTO> updateBoard(@AuthenticationPrincipal @RequestBody BoardUpdateRequestDTO requestDTO, @PathVariable Long courseId, @RequestParam Long courseBoardId){
        BoardUpdateResponseDTO updated = courseBoardService.update(requestDTO,courseId,courseBoardId);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/v1/board/{courseId}")
    public ResponseEntity<BoardDeleteResponseDTO> deleteBoard(@PathVariable Long courseId, @RequestParam Long courseBoardId){
        BoardDeleteResponseDTO deleted = courseBoardService.delete(courseId,courseBoardId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}
