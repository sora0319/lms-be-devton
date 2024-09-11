package com.example.ahimmoyakbackend.global.repository;

import com.example.ahimmoyakbackend.global.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}