package com.example.ahimmoyakbackend.live.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiveStreamingRepository extends JpaRepository<LiveStreaming, Long> {
    List<LiveStreaming> findByCourse(Course course);

    List<LiveStreaming> findByCourse_Tutor(User tutor);

}