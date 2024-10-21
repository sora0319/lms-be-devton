package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.dto.CommentCreateRequestDto;
import com.example.ahimmoyakbackend.board.dto.CommentCreateResponseDto;
import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import com.example.ahimmoyakbackend.board.entity.CourseComment;
import com.example.ahimmoyakbackend.board.repository.CourseBoardRepository;
import com.example.ahimmoyakbackend.board.repository.CourseCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseCommentService {
    private final CourseBoardRepository courseBoardRepository;
    private final CourseCommentRepository courseCommentRepository;

    public CommentCreateResponseDto create(User user, CommentCreateRequestDto requestDto, Long courseBoardId) {
        CourseBoard board = courseBoardRepository.findById(courseBoardId).orElseThrow(()->new IllegalArgumentException("없는 게시물 입니다."));
        CourseComment comment = CourseComment.builder()
                .content(requestDto.getContent())
                .courseBoard(board)
                .build();
        courseCommentRepository.save(comment);
        return CommentCreateResponseDto.builder().msg("댓글 작성 성공").build();
    }

    public CommentCreateResponseDto deleteComment(User user, Long courseBoardId, Long commentId) {
        CourseBoard board = courseBoardRepository.findById(courseBoardId).orElseThrow(()->new IllegalArgumentException("없는 게시물 입니다."));
        CourseComment deleted = courseCommentRepository.findByIdAndUserAndCourseBoard(commentId,user,board);
        courseCommentRepository.delete(deleted);
        return CommentCreateResponseDto.builder().msg("댓글 삭제 성공").build();
    }
}
