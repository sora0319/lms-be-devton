package com.example.ahimmoyakbackend.contents.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.contents.dto.ContentsUploadRequestDTO;
import com.example.ahimmoyakbackend.contents.service.ContentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class ContentsController {

    private final ContentsService contentsService;

    @PostMapping("/contents/upload")
    public ResponseEntity<ContentsUploadRequestDTO> contentsUpload(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long curriculumId, ContentsUploadRequestDTO dto, @RequestPart MultipartFile file) throws IOException {

        ContentsUploadRequestDTO contents = contentsService.contentsUpload(curriculumId, dto, userDetails.getUser(), file);

        return ResponseEntity.status(HttpStatus.OK).body(contents);
    }


}
