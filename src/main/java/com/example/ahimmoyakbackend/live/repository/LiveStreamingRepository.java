package com.example.ahimmoyakbackend.live.repository;

import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveStreamingRepository extends JpaRepository<LiveStreaming, Long> {
}