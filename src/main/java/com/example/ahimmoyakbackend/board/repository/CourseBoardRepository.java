package com.example.ahimmoyakbackend.board.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import com.example.ahimmoyakbackend.course.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseBoardRepository extends JpaRepository<CourseBoard, Long> {

    List<CourseBoard> findByCourseAndType(Course course, BoardType type);
    Page<CourseBoard> findByCourseAndType(Course course, BoardType type, Pageable pageable);

    List<CourseBoard> findByUserAndType(User user, BoardType type);
    Page<CourseBoard> findByUserAndType(User user, BoardType type, Pageable pageable);

    List<CourseBoard> findByCourse_Tutor(User tutor);

}