package com.example.ahimmoyakbackend.institution.repository;

import com.example.ahimmoyakbackend.institution.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Optional<Tutor> findByUserName(String username);
}