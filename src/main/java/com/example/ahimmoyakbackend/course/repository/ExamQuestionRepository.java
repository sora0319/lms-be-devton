package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.entity.ExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {
}