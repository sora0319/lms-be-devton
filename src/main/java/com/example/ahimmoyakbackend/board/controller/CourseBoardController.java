package com.example.ahimmoyakbackend.board.controller;

import com.example.ahimmoyakbackend.board.common.CourseBoardType;
import com.example.ahimmoyakbackend.board.dto.BoardCreateRequestDTO;
import com.example.ahimmoyakbackend.board.dto.BoardCreateResponseDTO;
import com.example.ahimmoyakbackend.board.service.CourseBoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "BoardController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseBoardController {

    private final CourseBoardService courseBoardService;

    @PostMapping("/v1/board/{type}/{courseId}")
    public ResponseEntity<BoardCreateResponseDTO> createBoard(@RequestBody BoardCreateRequestDTO requestDTO, @PathVariable Long courseId,@PathVariable CourseBoardType type){
        BoardCreateResponseDTO created = courseBoardService.create(requestDTO,courseId,type);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

}
