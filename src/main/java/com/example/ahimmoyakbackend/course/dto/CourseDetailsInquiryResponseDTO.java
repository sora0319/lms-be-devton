package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.company.entity.Contract;
import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.curriculum.dto.CurriculumInquiryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailsInquiryResponseDTO {
    private String title;
    private String introduction;
    private CourseCategory category;
    private String institutionName; // Institution 엔티티에서 name 들고옴
    private String tutorName; // tutor 엔티티 name
    private LocalDate beginDate; // contract 에서 시작날짜 종료날짜
    private LocalDate endDate;
    private List<CurriculumInquiryResponseDTO> curriculums;
    private Integer totalContents;  // 진행률 계산을 위한 총 콘텐츠 수
    private Integer completedContents;  // 진행률 계산을 위한 완료된 콘텐츠 수

    public static CourseDetailsInquiryResponseDTO from(Course course, Contract contract) {
        return CourseDetailsInquiryResponseDTO.builder()
                .title(course.getTitle())
                .introduction(course.getIntroduction())
                .category(course.getCategory())
                .institutionName(course.getInstitution().getName())
                .tutorName(course.getTutor().getUser().getName())
                .beginDate(contract.getBeginDate())
                .endDate(contract.getEndDate())
                .build();
    }

    public CourseDetailsInquiryResponseDTO setCurriculums(List<CurriculumInquiryResponseDTO> curriculumDTOList){
        this.curriculums = curriculumDTOList;
        return this;
    }

    public CourseDetailsInquiryResponseDTO setTotalContents(int totalContents){
        this.totalContents = totalContents;
        return this;
    }

    public CourseDetailsInquiryResponseDTO setCompletedContents(int completedContents) {
        this.completedContents = completedContents;
        return this;
    }

}
