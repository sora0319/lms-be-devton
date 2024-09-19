package com.example.ahimmoyakbackend.company.repository;

import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.company.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
    List<Affiliation> findAllByDepartment_CompanyIdAndDepartmentId(Long department_company_id, Long department_id);

}