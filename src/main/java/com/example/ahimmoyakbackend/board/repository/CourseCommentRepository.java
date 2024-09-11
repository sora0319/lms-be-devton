package com.example.ahimmoyakbackend.board.repository;

import com.example.ahimmoyakbackend.board.entity.CourseComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseCommentRepository extends JpaRepository<CourseComment, Long> {
}