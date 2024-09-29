package com.example.ahimmoyakbackend.company.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<CourseProvide, Long> {
    Optional<CourseProvide> findByCourseAndEnrollments_User(Course course, User user);

    List<CourserProvide> findAllByCompanyAndCourse_Institution(Company company, Institution institution);
}