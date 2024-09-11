package com.example.ahimmoyakbackend.institution.repository;


import com.example.ahimmoyakbackend.institution.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}