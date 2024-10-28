package com.example.ahimmoyakbackend.course.controller;


import com.example.ahimmoyakbackend.course.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileServerController {

    private final FileService fileService;

    @GetMapping("/video/{info}")
    public ResponseEntity<ResourceRegion> streamVideo(@RequestHeader HttpHeaders httpHeaders, @PathVariable String info) {
        return fileService.streamVideo(httpHeaders, info);
    }

    @GetMapping("/material/{info}")
    public ResponseEntity<Resource> downloadMaterial(@PathVariable String info) {
        return fileService.downloadMaterial(info);
    }
}
