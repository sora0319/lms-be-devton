package com.example.ahimmoyakbackend.board.repository;

import com.example.ahimmoyakbackend.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardId(Long board_id);
}