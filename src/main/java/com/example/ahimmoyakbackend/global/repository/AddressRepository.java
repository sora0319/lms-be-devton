package com.example.ahimmoyakbackend.global.repository;

import com.example.ahimmoyakbackend.global.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}