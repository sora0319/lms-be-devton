package com.example.ahimmoyakbackend.board.repository;

import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseBoardRepository extends JpaRepository<CourseBoard, Long> {
}