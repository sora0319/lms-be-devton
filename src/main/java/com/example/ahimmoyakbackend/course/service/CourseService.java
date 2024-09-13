package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.company.entity.Contract;
import com.example.ahimmoyakbackend.contents.dto.ContentsInquiryResponseDTO;
import com.example.ahimmoyakbackend.course.common.ContentsHistoryState;
import com.example.ahimmoyakbackend.course.dto.CourseDetailsInquiryResponseDTO;
import com.example.ahimmoyakbackend.course.entity.Contents;
import com.example.ahimmoyakbackend.course.entity.ContentsHistory;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.ContentsHistoryRepository;
import com.example.ahimmoyakbackend.course.repository.ContentsRepository;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.curriculum.dto.CurriculumInquiryResponseDTO;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import com.example.ahimmoyakbackend.global.repository.ContractRepository;
import com.example.ahimmoyakbackend.institution.entity.Tutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ContentsHistoryRepository contentsHistoryRepository;
    private final ContentsRepository contentsRepository;
    private final ContractRepository contractRepository;


    @Transactional
    public CourseDetailsInquiryResponseDTO Inquiry(Long userId, Long courseId, ContentsHistoryState state) {

        Course findCourseId = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("해당 코스를 찾을 수 없습니다."));

        Tutor tutor = findCourseId.getTutor();

        List<CurriculumInquiryResponseDTO> curriculumResponseDTOList = findCourseId.getCurriculumList().stream()
            .map(curriculum -> {
                double progress = calculateProgress(curriculum.getContentsList(), userId);

                return CurriculumInquiryResponseDTO.builder()
                        .curriculumTitle(curriculum.getTitle())
                        .curriculumId(curriculum.getId())
                        .curriculumIdx(curriculum.getIdx())
                        .contentsProgress(progress)
                        .contentList(curriculum.getContentsList().stream()
                                .map(contents -> ContentsInquiryResponseDTO.builder()
                                        .contentsId(contents.getId())
                                        .title(contents.getTitle())
                                        .contentsIdx(contents.getIdx())
                                        .build())
                                .collect(Collectors.toList()))
                        .build();
            }).collect(Collectors.toList());
        return CourseDetailsInquiryResponseDTO.builder()
                .courseTitle(findCourseId.getTitle())
                .tutorName(tutor.getUser().getName())
                .courseProgress(state)
                .startDate(new Timestamped())
                .endDate(new Timestamped())
                .courseImage("")
                .introduction(findCourseId.getIntroduction())
                .curriculumInquiryResponseDTO(curriculumResponseDTOList)
                .build();

    }

    // 유저의 콘텐츠 상태
    public ContentsHistoryState getStateByUserAndCourse(Long userId, Long contentId) {
        ContentsHistory contentsHistory = contentsHistoryRepository.findByContents_IdAndEnrollment_User_Id(userId, contentId);

        return contentsHistory.getState();
    }

    // 진행률 계산
    public double calculateProgress(List<Contents> contentsList, Long userId) {
        int totalContents = contentsList.size();
        long count = contentsList.stream()
                .map(contents -> contentsHistoryRepository.findByContents_IdAndEnrollment_User_Id(userId, contents.getId()))
                .filter(contentsHistory -> contentsHistory.getState() == ContentsHistoryState.COMPLETED)
                .count();

        if (totalContents == 0) {
            return 0;
        } else {

            return (double) count / totalContents * 100;  //  총 contentList 사이즈에 Completed 인 것을 나눠서 백분율
        }
    }
}
