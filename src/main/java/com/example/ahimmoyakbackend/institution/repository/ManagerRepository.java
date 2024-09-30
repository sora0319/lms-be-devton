package com.example.ahimmoyakbackend.institution.repository;

import com.example.ahimmoyakbackend.institution.entity.Institution;
import com.example.ahimmoyakbackend.institution.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Manager findByInstitution(Institution institution);
}