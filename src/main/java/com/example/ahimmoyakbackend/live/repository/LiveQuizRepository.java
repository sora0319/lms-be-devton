package com.example.ahimmoyakbackend.live.repository;

import com.example.ahimmoyakbackend.live.entity.LiveQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveQuizRepository extends JpaRepository<LiveQuiz, Long> {
}