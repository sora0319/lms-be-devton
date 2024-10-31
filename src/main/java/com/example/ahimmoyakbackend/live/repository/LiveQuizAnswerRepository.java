package com.example.ahimmoyakbackend.live.repository;

import com.example.ahimmoyakbackend.live.entity.LiveQuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveQuizAnswerRepository extends JpaRepository<LiveQuizAnswer, Long> {
}