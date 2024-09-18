package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.company.entity.Contract;
import com.example.ahimmoyakbackend.contents.dto.ContentsInquiryResponseDTO;
import com.example.ahimmoyakbackend.course.common.ContentsHistoryState;
import com.example.ahimmoyakbackend.course.dto.CourseDetailsInquiryResponseDTO;
import com.example.ahimmoyakbackend.course.entity.Contents;
import com.example.ahimmoyakbackend.course.entity.ContentsHistory;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import com.example.ahimmoyakbackend.course.repository.ContentsHistoryRepository;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.curriculum.dto.CurriculumInquiryResponseDTO;
import com.example.ahimmoyakbackend.global.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ContentsHistoryRepository contentsHistoryRepository;
    private final ContractRepository contractRepository;


    @Transactional
    public CourseDetailsInquiryResponseDTO Inquiry(User user, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("코스가 없습니다."));
        Contract contract = contractRepository.findByCourseAndEnrollments_User(course, user).orElseThrow(() -> new IllegalArgumentException("컨텐츠가 없습니다."));
        CourseDetailsInquiryResponseDTO courseDto = CourseDetailsInquiryResponseDTO.from(course, contract);

        List<CurriculumInquiryResponseDTO> curriculumDTOList = new ArrayList<>();
        int courseTotalContents = 0;
        int courseCompletedContents = 0;

        for (Curriculum curriculum : course.getCurriculumList()) {

            CurriculumInquiryResponseDTO curriculumDto = CurriculumInquiryResponseDTO.from(curriculum);
            List<ContentsInquiryResponseDTO> contentsDTOList = new ArrayList<>();

            int completedContents = 0;

            for (Contents contents : curriculum.getContentsList()) {

                ContentsHistory history = contentsHistoryRepository.findByContentsAndEnrollment_User(contents, user).orElseThrow(()-> new IllegalArgumentException("컨텐츠의 히스토리가 없습니다."));

                ContentsInquiryResponseDTO contentsDTO = ContentsInquiryResponseDTO.from(contents, history.getState());

                courseTotalContents++;

                if (history.getState().equals(ContentsHistoryState.COMPLETED)) {
                    courseCompletedContents++;
                    completedContents++;
                }
                contentsDTOList.add(contentsDTO);
            }
            curriculumDto.setCompletedContents(completedContents)
                    .setContents(contentsDTOList);
            curriculumDTOList.add(curriculumDto);
        }
        courseDto.setCompletedContents(courseCompletedContents)
                .setTotalContents(courseTotalContents)
                .setCurriculums(curriculumDTOList);
        return courseDto;

    }
}
