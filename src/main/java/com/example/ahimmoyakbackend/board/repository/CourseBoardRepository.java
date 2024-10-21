package com.example.ahimmoyakbackend.board.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.entity.CourseBoard;
import com.example.ahimmoyakbackend.course.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseBoardRepository extends JpaRepository<CourseBoard, Long> {
    CourseBoard findByCourseAndIdAndUser(Course course,Long courseBoardId,User user);
    CourseBoard findByCourseAndId(Course course,Long courseBoardId);
    Page<CourseBoard> findAllByCourseAndTypeOrderByCreatedAtDesc(Course course, BoardType type, Pageable pageable);
    Page<CourseBoard> findAllByUserAndCourseOrderByCreatedAtDesc(User user, Course course, Pageable pageable);
}