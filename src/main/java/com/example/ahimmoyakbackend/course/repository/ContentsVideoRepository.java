package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.entity.Contents;
import com.example.ahimmoyakbackend.course.entity.ContentsVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsVideoRepository extends JpaRepository<ContentsVideo, Long> {
    ContentsVideo findByContents(Contents contents);
}