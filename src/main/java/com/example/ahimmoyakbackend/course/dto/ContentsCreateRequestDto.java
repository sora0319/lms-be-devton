package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.course.common.ContentType;
import com.example.ahimmoyakbackend.course.entity.Contents;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
public record ContentsCreateRequestDto(
        String title,
        ContentType type,
        MultipartFile file
) {
    public Contents toDto(Curriculum curriculum, int idx) {
        return Contents.builder()
                .title(this.title)
                .type(this.type)
                .curriculum(curriculum)
                .idx(idx)
                .build();
    }
}