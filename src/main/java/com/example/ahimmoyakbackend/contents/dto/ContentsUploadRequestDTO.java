package com.example.ahimmoyakbackend.contents.dto;

import com.example.ahimmoyakbackend.course.common.ContentType;
import com.example.ahimmoyakbackend.course.entity.Contents;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentsUploadRequestDTO {

    private String title;
    private ContentType type;
    private String contentsPath;

    public static ContentsUploadRequestDTO from(Contents contents) {
        return ContentsUploadRequestDTO.builder()
                .title(contents.getTitle())
                .type(contents.getType())
                .contentsPath(builder().contentsPath)
                .build();
    }
}
