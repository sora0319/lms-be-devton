package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.course.common.ContentType;
import com.example.ahimmoyakbackend.course.entity.Contents;
import lombok.Builder;

@Builder
public record ContentsInfoResponseDto(
        String title,
        ContentType contentType,
        String fileInfo
) {
    public static ContentsInfoResponseDto from(Contents contents, String fileInfo) {
        return ContentsInfoResponseDto.builder()
                .title(contents.getTitle())
                .contentType(contents.getType())
                .fileInfo(fileInfo)
                .build();
    }
}
