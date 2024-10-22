package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.course.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findAllByCourseProvideId(Long courseProvideId);
    Enrollment findByUser(User user);
    Enrollment findByCourseProvide_Company_IdAndCourseProvide_Course_Id(Long company_id, Long course_id);
}