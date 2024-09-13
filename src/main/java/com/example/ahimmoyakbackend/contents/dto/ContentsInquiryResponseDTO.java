package com.example.ahimmoyakbackend.contents.dto;

import com.example.ahimmoyakbackend.course.common.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentsInquiryResponseDTO {
    private Long contentsId;
    private String title;
    private Integer contentsIdx;
    private ContentType type;

}
