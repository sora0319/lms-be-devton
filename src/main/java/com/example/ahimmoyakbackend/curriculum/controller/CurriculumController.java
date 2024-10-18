package com.example.ahimmoyakbackend.curriculum.controller;

import com.example.ahimmoyakbackend.curriculum.dto.CurriculumChangeOrderResponseDTO;
import com.example.ahimmoyakbackend.curriculum.service.CurriculumService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class CurriculumController {

    private final CurriculumService curriculumService;

    @PatchMapping("curriculum/changeOrder")
    public ResponseEntity<CurriculumChangeOrderResponseDTO> curriculumChangeOrder(
            @RequestParam Long courseId,
            @RequestParam Long target1,
            @RequestParam Long target2
    ) {
        CurriculumChangeOrderResponseDTO responseDTO = curriculumService.changeOrder(courseId, target1, target2);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);


    }
}
