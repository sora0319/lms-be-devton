package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.dto.BoardCreateRequestDto;
import com.example.ahimmoyakbackend.board.dto.BoardListResponseDto;
import com.example.ahimmoyakbackend.board.dto.BoardPageResponseDto;
import com.example.ahimmoyakbackend.board.dto.CommentListResponseDto;
import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import com.example.ahimmoyakbackend.board.repository.CourseBoardRepository;
import com.example.ahimmoyakbackend.board.repository.CourseCommentRepository;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseBoardServiceImpl implements CourseBoardService {

    private final CourseRepository courseRepository;
    private final CourseBoardRepository courseBoardRepository;
    private final CourseCommentRepository courseCommentRepository;
    private final UserService userService;

    @Override
    @Transactional
    public boolean create(UserDetails userDetails, long courseId, BoardType boardType, BoardCreateRequestDto requestDto) {
        User user = userService.getAuth(userDetails);
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "코스를 찾을 수 없습니다.");
        }
        if (boardType.equals(BoardType.NOTICE) && !course.getTutor().equals(user)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "강사만 공지를 쓸 수 있습니다.");
        }
        courseBoardRepository.save(requestDto.toEntity(user, course, boardType));
        return true;
    }

    @Override
    @Transactional
    public boolean update(UserDetails userDetails, long boardId, BoardCreateRequestDto requestDto) {
        User user = userService.getAuth(userDetails);
        CourseBoard courseBoard = courseBoardRepository.findById(boardId).orElse(null);
        if (courseBoard == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
        }
        if (!courseBoard.getUser().equals(user)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "본인 게시글만 수정할 수 있습니다.");
        }
        courseBoardRepository.save(courseBoard.patch(requestDto));
        return true;
    }

    @Override
    @Transactional
    public boolean delete(UserDetails userDetails, long boardId) {
        User user = userService.getAuth(userDetails);
        CourseBoard courseBoard = courseBoardRepository.findById(boardId).orElse(null);
        if (courseBoard == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
        }
        if (!courseBoard.getUser().equals(user)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "본인 게시글만 삭제할 수 있습니다.");
        }
        courseBoardRepository.delete(courseBoard);
        return true;
    }

    @Override
    public List<BoardListResponseDto> getList(long courseId, BoardType boardType) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "코스를 찾을 수 없습니다");
        }
        return courseBoardRepository.findByCourseAndType(course, boardType).stream()
                .map(board -> BoardListResponseDto
                        .from(board, courseCommentRepository.countByCourseBoard(board)))
                .toList();
    }

    @Override
    public Page<BoardListResponseDto> getList(long courseId, BoardType boardType, Pageable pageable) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "코스를 찾을 수 없습니다");
        }
        return courseBoardRepository.findByCourseAndType(course, boardType, pageable)
                .map(board -> BoardListResponseDto
                        .from(board, courseCommentRepository.countByCourseBoard(board)));
    }

    @Override
    public BoardPageResponseDto getBoard(long boardId) {
        CourseBoard courseBoard = courseBoardRepository.findById(boardId).orElse(null);
        if (courseBoard == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
        }
        return BoardPageResponseDto
                .from(courseBoard, courseBoard.getComments().stream()
                        .map(CommentListResponseDto::from).toList());
    }

    @Override
    public List<BoardListResponseDto> getListByUser(UserDetails userDetails, BoardType boardType) {
        User user = userService.getAuth(userDetails);
        return courseBoardRepository.findByUserAndType(user, boardType).stream()
                .map(board -> BoardListResponseDto
                        .from(board, courseCommentRepository.countByCourseBoard(board)))
                .toList();
    }

    @Override
    public Page<BoardListResponseDto> getListByUser(UserDetails userDetails, BoardType boardType, Pageable pageable) {
        User user = userService.getAuth(userDetails);
        return courseBoardRepository.findByUserAndType(user, boardType, pageable)
                .map(board -> BoardListResponseDto
                        .from(board, courseCommentRepository.countByCourseBoard(board)));
    }
}
