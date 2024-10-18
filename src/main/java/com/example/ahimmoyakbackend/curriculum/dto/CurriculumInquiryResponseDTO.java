package com.example.ahimmoyakbackend.curriculum.dto;

import com.example.ahimmoyakbackend.contents.dto.ContentsInquiryResponseDTO;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
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
    private Integer totalContents;
    private Integer completedContents;  // 진행률 계산을 위한 총 콘텐츠 수
    private Integer curriculumIdx;  // 진행률 계산을 위한 완료된 콘텐츠 수
    private List<ContentsInquiryResponseDTO> contents;

    public static CurriculumInquiryResponseDTO from(Curriculum curriculum) {
        return CurriculumInquiryResponseDTO.builder()
                .curriculumId(curriculum.getId())
                .totalContents(curriculum.getContentsList().size())
                .curriculumIdx(curriculum.getIdx())
                .build();
    }

    public CurriculumInquiryResponseDTO setCompletedContents(int completedContents) {
        this.completedContents = completedContents;
        return this;
    }

    public CurriculumInquiryResponseDTO setContents(List<ContentsInquiryResponseDTO> contentsInquiryResponseDTOList) {
        this.contents = contentsInquiryResponseDTOList;
        return this;
    }
}
