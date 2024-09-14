package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.dto.*;
import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import com.example.ahimmoyakbackend.board.repository.CourseBoardRepository;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseBoardService {

    private final CourseBoardRepository courseBoardRepository;
    private final CourseRepository courseRepository;

    public BoardCreateResponseDto create(BoardCreateRequestDto requestDTO, Long courseId, BoardType type) {

        Course course = courseRepository.findById(courseId).orElseThrow(()->new IllegalArgumentException("잘못된 코스입니다."));

        CourseBoard board = CourseBoard.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .type(type)
                .course(course)
                .build();
        courseBoardRepository.save(board);
        return BoardCreateResponseDto.builder().msg("게시물 작성 완료").build();
    }

    public BoardUpdateResponseDto update(BoardUpdateRequestDto requestDTO, Long courseId, Long courseBoardId) {
        CourseBoard updated = courseBoardRepository.findById(courseBoardId).orElseThrow(()->new IllegalArgumentException("없는 게시물 입니다."));
        updated.patch(requestDTO, courseBoardId,courseId);
        courseBoardRepository.save(updated);
        return BoardUpdateResponseDto.builder().msg("게시물 수정 완료").build();
    }

    public BoardDeleteResponseDto delete(Long courseId, Long courseBoardId) {
        CourseBoard deleted = courseBoardRepository.findById(courseBoardId).orElseThrow(()->new IllegalArgumentException("없는 게시물 입니다."));
        if(deleted.getCourse().getId() != courseId){
            throw new IllegalArgumentException("잘못된 게시물 입니다.");
        }
        courseBoardRepository.delete(deleted);
        return BoardDeleteResponseDto.builder().msg("게시물 삭제 완료").build();
    }

    public CourseBoardInquiryResponseDto inquiry(Long courseId, BoardType type, int page, int size) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("없는 코스 입니다."));

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CourseBoard> courseBoardPage = courseBoardRepository
                .findAllByCourseAndTypeOrderByCreatedAtDesc(course, type, pageable);

        List<CourseBoardsResponseDto> boards = courseBoardPage
                .stream()
                .map(CourseBoard::toBoardResponseDto)
                .collect(Collectors.toList());

        return new CourseBoardInquiryResponseDto(
                course.getTitle(),
                boards,
                new Pagination(page,size)
        );
    }
}
