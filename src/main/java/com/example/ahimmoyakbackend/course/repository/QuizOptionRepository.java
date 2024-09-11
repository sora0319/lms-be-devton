package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.entity.QuizOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizOptionRepository extends JpaRepository<QuizOption, Long> {
}