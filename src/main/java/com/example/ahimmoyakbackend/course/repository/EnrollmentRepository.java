package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Enrollment findByCourseProvide_Company_IdAndCourseProvide_Course_Id(Long company_id, Long course_id);
}