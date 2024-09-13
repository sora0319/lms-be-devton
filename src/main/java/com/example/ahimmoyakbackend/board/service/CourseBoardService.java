package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.board.common.CourseBoardType;
import com.example.ahimmoyakbackend.board.dto.BoardCreateRequestDTO;
import com.example.ahimmoyakbackend.board.dto.BoardCreateResponseDTO;
import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import com.example.ahimmoyakbackend.board.repository.CourseBoardRepository;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseBoardService {

    private final CourseBoardRepository courseBoardRepository;
    private final CourseRepository courseRepository;


    public BoardCreateResponseDTO create(BoardCreateRequestDTO requestDTO, Long courseId, CourseBoardType type) {

        Course course = courseRepository.findById(courseId).orElseThrow(()->new IllegalArgumentException("잘못된 코스입니다."));

        CourseBoard board = CourseBoard.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .type(type)
                .course(course)
                .build();
        courseBoardRepository.save(board);
        return BoardCreateResponseDTO.builder().msg("게시물 작성 완료").build();
    }
}
