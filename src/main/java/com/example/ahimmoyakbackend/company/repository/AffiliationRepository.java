package com.example.ahimmoyakbackend.company.repository;

import com.example.ahimmoyakbackend.company.entity.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
    List<Affiliation> findAllByCompanyIdAndDepartmentId(Long companyId, Long departmentId);
}