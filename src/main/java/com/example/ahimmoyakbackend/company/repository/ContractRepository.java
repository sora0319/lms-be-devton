package com.example.ahimmoyakbackend.company.repository;

import com.example.ahimmoyakbackend.company.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}