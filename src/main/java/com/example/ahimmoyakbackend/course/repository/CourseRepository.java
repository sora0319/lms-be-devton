package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.institution.entity.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findAllByCategory(CourseCategory category, Pageable pageable);

    List<Course> findByTutor(Tutor tutor);

    @Query(value = "SELECT * FROM Course WHERE category = :category ORDER BY RAND() LIMIT :size", nativeQuery = true)
    List<Course> findRandomCoursesBySize(@Param("category") CourseCategory category, @Param("size") int size);
}