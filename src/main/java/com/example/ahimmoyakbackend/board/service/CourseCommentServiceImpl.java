package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.board.dto.CommentWriteRequestDto;
import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import com.example.ahimmoyakbackend.board.entity.CourseComment;
import com.example.ahimmoyakbackend.board.repository.CourseBoardRepository;
import com.example.ahimmoyakbackend.board.repository.CourseCommentRepository;
import com.example.ahimmoyakbackend.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseCommentServiceImpl implements CourseCommentService {

    private final UserService userService;
    private final CourseCommentRepository courseCommentRepository;
    private final CourseBoardRepository courseBoardRepository;

    @Override
    public boolean write(UserDetails userDetails, long boardId, CommentWriteRequestDto requestDto) {
        User user = userService.getAuth(userDetails);
        CourseBoard courseBoard = courseBoardRepository.findById(boardId).orElse(null);
        if(courseBoard == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
        }
        courseCommentRepository.save(requestDto.toEntity(user, courseBoard));
        return true;
    }

    @Override
    public boolean edit(UserDetails userDetails, long commentId, CommentWriteRequestDto requestDto) {
        User user = userService.getAuth(userDetails);
        CourseComment courseComment = courseCommentRepository.findById(commentId).orElse(null);
        if(courseComment == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.");
        }
        if(!courseComment.getUser().equals(user)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "댓글 작성자 본인만 수정가능합니다.");
        }
        courseCommentRepository.save(courseComment.patch(requestDto));
        return true;
    }

    @Override
    public boolean delete(UserDetails userDetails, long commentId) {
        User user = userService.getAuth(userDetails);
        CourseComment courseComment = courseCommentRepository.findById(commentId).orElse(null);
        if(courseComment == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.");
        }
        if(!courseComment.getUser().equals(user)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "댓글 작성자 본인만 삭제가능합니다.");
        }
        courseCommentRepository.delete(courseComment);
        return true;
    }
}
