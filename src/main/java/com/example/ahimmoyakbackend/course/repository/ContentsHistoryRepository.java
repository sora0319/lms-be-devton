package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.entity.ContentsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentsHistoryRepository extends JpaRepository<ContentsHistory, Long> {
    ContentsHistory findByContents_IdAndEnrollment_User_Id(Long contentsId, Long userId);
}