package com.example.ahimmoyakbackend.company.repository;

import com.example.ahimmoyakbackend.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByNameContaining(String name);

    boolean existsByBusinessNumber(String businessNumber);

    boolean existsByEmailDomain(String emailDomain);

    List<Company> findAllById(Long id);
}