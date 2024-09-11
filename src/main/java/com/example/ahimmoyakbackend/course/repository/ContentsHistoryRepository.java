package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.entity.ContentsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsHistoryRepository extends JpaRepository<ContentsHistory, Long> {
}