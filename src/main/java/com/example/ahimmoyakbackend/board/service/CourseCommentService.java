package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.board.dto.CommentWriteRequestDto;
import com.example.ahimmoyakbackend.course.entity.Course;
import org.springframework.security.core.userdetails.UserDetails;

public interface CourseCommentService {
    public boolean write(UserDetails userDetails, long boardId, CommentWriteRequestDto requestDto);
    public boolean edit(UserDetails userDetails, long commentId, CommentWriteRequestDto requestDto);
    public boolean delete(UserDetails userDetails, long commentId);
}
