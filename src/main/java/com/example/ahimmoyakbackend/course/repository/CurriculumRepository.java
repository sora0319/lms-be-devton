package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
}