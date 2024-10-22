package com.example.ahimmoyakbackend.live.repository;

import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.institution.entity.Tutor;
import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiveStreamingRepository extends JpaRepository<LiveStreaming, Long> {
    List<LiveStreaming> findByCourseProvide(CourseProvide courseProvide);

    List<LiveStreaming> findByCourseProvide_Course_Tutor(Tutor tutor);
}