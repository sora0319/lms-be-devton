package com.example.ahimmoyakbackend.board.repository;

import com.example.ahimmoyakbackend.board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}