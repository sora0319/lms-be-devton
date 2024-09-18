package com.example.ahimmoyakbackend.global.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.company.entity.Contract;
import com.example.ahimmoyakbackend.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    Optional<Contract> findByCourseAndEnrollments_User(Course course, User user);
}