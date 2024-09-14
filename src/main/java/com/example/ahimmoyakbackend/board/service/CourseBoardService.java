package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.common.CourseBoardType;
import com.example.ahimmoyakbackend.board.dto.*;
import com.example.ahimmoyakbackend.board.entity.Board;
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


    public BoardCreateResponseDTO create(BoardCreateRequestDTO requestDTO, Long courseId, BoardType type) {

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

    public BoardUpdateResponseDTO update(BoardUpdateRequestDTO requestDTO, Long courseId, Long courseBoardId) {
        CourseBoard updated = courseBoardRepository.findById(courseBoardId).orElseThrow(()->new IllegalArgumentException("없는 게시물 입니다."));
        updated.patch(requestDTO, courseBoardId,courseId);
        courseBoardRepository.save(updated);
        return BoardUpdateResponseDTO.builder().msg("게시물 수정 완료").build();
    }

    public BoardDeleteResponseDTO delete(Long courseId, Long courseBoardId) {
        CourseBoard deleted = courseBoardRepository.findById(courseBoardId).orElseThrow(()->new IllegalArgumentException("없는 게시물 입니다."));
        if(deleted.getCourse().getId() != courseId){
            throw new IllegalArgumentException("잘못된 게시물 입니다.");
        }
        courseBoardRepository.delete(deleted);
        return BoardDeleteResponseDTO.builder().msg("게시물 삭제 완료").build();
    }
}
