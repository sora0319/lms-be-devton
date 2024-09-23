package com.example.ahimmoyakbackend.enrollment.dto;

import com.example.ahimmoyakbackend.company.entity.Contract;
import com.example.ahimmoyakbackend.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentGetListResponseDTO {

    private String title; // 타이틀
    private String institutionName; // 기관 명
    private String instructorName; // 강사 이름
    private Integer attendeeCount; // 참석자 수
    private LocalDate beginDate; // 시작 날짜
    private LocalDate endDate; // 끝나는 날짜
    private LocalDate remainDate; // 남아있는 날짜


}
