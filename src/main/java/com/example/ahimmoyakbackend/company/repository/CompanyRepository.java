package com.example.ahimmoyakbackend.company.repository;

import com.example.ahimmoyakbackend.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}