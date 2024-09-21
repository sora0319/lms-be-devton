package com.example.ahimmoyakbackend.company.repository;

import com.example.ahimmoyakbackend.company.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
}