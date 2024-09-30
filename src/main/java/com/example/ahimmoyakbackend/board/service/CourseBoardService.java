package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.dto.*;
import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import com.example.ahimmoyakbackend.board.entity.CourseComment;
import com.example.ahimmoyakbackend.board.repository.CourseBoardRepository;
import com.example.ahimmoyakbackend.board.repository.CourseCommentRepository;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import com.example.ahimmoyakbackend.institution.entity.Manager;
import com.example.ahimmoyakbackend.institution.repository.InstitutionRepository;
import com.example.ahimmoyakbackend.institution.repository.ManagerRepository;
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
    private final CourseCommentRepository courseCommentRepository;
    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;
    private final ManagerRepository managerRepository;

    public BoardCreateResponseDto create(User user, BoardCreateRequestDto requestDTO, Long courseId, BoardType type) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("잘못된 코스입니다."));
        Institution institution = institutionRepository.findById(course.getInstitution().getId()).orElseThrow(() -> new IllegalArgumentException("잘못된 기관입니다."));
        Manager manager = managerRepository.findByInstitution(institution);

        if (type.equals(BoardType.NOTICE) && (user.getId().equals(course.getTutor().getId()) || user.getId().equals(manager.getUser().getId()))) {
            String msg = createdBoard(user, requestDTO, type, course);
            return BoardCreateResponseDto.builder().msg(msg).build();
        }
        if (type.equals(BoardType.QNA)) {
            String msg = createdBoard(user, requestDTO, type, course);
            return BoardCreateResponseDto.builder().msg(msg).build();
        } else {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
    }

    public BoardUpdateResponseDto update(User user, BoardUpdateRequestDto requestDTO, Long courseId, Long courseBoardId) {
        User user1 = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("잘못된 유저입니다."));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("잘못된 코스입니다."));
        CourseBoard updated = courseBoardRepository.findByCourseAndIdAndUser(course, courseBoardId, user1);
        Institution institution = institutionRepository.findById(course.getInstitution().getId()).orElseThrow(() -> new IllegalArgumentException("잘못된 기관입니다."));
        Manager manager = managerRepository.findByInstitution(institution);
        if (updated.getType().equals(BoardType.NOTICE) && (user1.getId().equals(course.getTutor().getId()) || user1.getId().equals(manager.getUser().getId()))) {
            updated.patch(requestDTO, courseBoardId, courseId);
            courseBoardRepository.save(updated);
            return BoardUpdateResponseDto.builder().msg("공지 게시물 수정 완료").build();
        }
        if (updated.getType().equals(BoardType.QNA)) {
            updated.patch(requestDTO, courseBoardId, courseId);
            courseBoardRepository.save(updated);
            return BoardUpdateResponseDto.builder().msg("질문 게시물 수정 완료").build();
        } else {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
    }

    public BoardDeleteResponseDto delete(User user, Long courseId, Long courseBoardId) {
        User user1 = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("잘못된 유저입니다."));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("잘못된 코스입니다."));
        Institution institution = institutionRepository.findById(course.getInstitution().getId()).orElseThrow(() -> new IllegalArgumentException("잘못된 기관입니다."));
        Manager manager = managerRepository.findByInstitution(institution);
        CourseBoard deleted = courseBoardRepository.findByCourseAndIdAndUser(course, courseBoardId, user1);

        if (deleted.getType().equals(BoardType.NOTICE) && (user1.getId().equals(course.getTutor().getId()) || user1.getId().equals(manager.getUser().getId()))) {
            courseBoardRepository.delete(deleted);
            return BoardDeleteResponseDto.builder().msg("공지 게시물 삭제 완료").build();
        } else if (deleted.getType().equals(BoardType.QNA)) {
            courseBoardRepository.delete(deleted);
            return BoardDeleteResponseDto.builder().msg("질문 게시물 삭제 완료").build();
        } else {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
    }

    public CourseBoardInquiryResponseDto inquiry(User user, Long courseId, BoardType type, int page, int size) {
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
                new Pagination(page, size)
        );
    }

    //내가 적은 게시물 조회
    public CourseBoardInquiryResponseDto inquiryCreatedBoard(User user, Long courseId, int page, int size) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("없는 코스 입니다."));

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CourseBoard> courseBoardPage = courseBoardRepository
                .findAllByUserAndCourseOrderByCreatedAtDesc(user, course, pageable);

        List<CourseBoardsResponseDto> boards = courseBoardPage
                .stream()
                .map(CourseBoard::toBoardResponseDto)
                .collect(Collectors.toList());

        return new CourseBoardInquiryResponseDto(
                course.getTitle(),
                boards,
                new Pagination(page, size)
        );
    }

    public CourseBoardShowResponseDto show(User user, Long courseId, BoardType type, Long courseBoardId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("없는 코스 입니다."));
        CourseBoard board = courseBoardRepository.findByCourseAndId(course, courseBoardId);

        return CourseBoardShowResponseDto.builder()
                .courseTitle(board.getCourse().getTitle())
                .username(board.getUser().getUsername())
                .title(board.getTitle())
                .content(board.getContent())
                .type(type)
                .createAt(board.getCreatedAt())
                .comments(courseCommentRepository.findAllByCourseBoardId(courseBoardId).stream().map(CourseComment::toDto).toList())
                .build();
    }

    public String createdBoard(User user, BoardCreateRequestDto requestDTO, BoardType type, Course course) {
        CourseBoard board = CourseBoard.builder()
                .user(user)
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .type(type)
                .course(course)
                .build();
        courseBoardRepository.save(board);
        return type+"게시글 작성 성공";
    }
}
