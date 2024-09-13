package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.course.common.ContentsHistoryState;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.curriculum.dto.CurriculumInquiryResponseDTO;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailsInquiryResponseDTO {

    private String courseTitle;
    private String tutorName;
    private ContentsHistoryState courseProgress;
    private Timestamped startDate;
    private Timestamped endDate;
    private String courseImage;
    private String introduction;
    private List<CurriculumInquiryResponseDTO> curriculumInquiryResponseDTO;

}
