package com.example.ahimmoyakbackend.contents.dto;

import com.example.ahimmoyakbackend.course.common.ContentType;
import com.example.ahimmoyakbackend.course.common.ContentsHistoryState;
import com.example.ahimmoyakbackend.course.entity.Contents;
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
    private ContentsHistoryState contentsState;

    public static ContentsInquiryResponseDTO from(Contents contents, ContentsHistoryState state) {
        return ContentsInquiryResponseDTO.builder()
                .contentsId(contents.getId())
                .title(contents.getTitle())
                .contentsIdx(contents.getIdx())
                .type(contents.getType())
                .contentsState(state)
                .build();
    }
}
