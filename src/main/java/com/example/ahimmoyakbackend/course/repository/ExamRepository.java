package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}