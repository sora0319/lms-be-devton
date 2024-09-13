package com.example.ahimmoyakbackend.curriculum.dto;

import com.example.ahimmoyakbackend.contents.dto.ContentsInquiryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumInquiryResponseDTO {

    private Long curriculumId;
    private Integer curriculumIdx;
    private String curriculumTitle;
    private Double contentsProgress;
    private List<ContentsInquiryResponseDTO> contentList;

}
