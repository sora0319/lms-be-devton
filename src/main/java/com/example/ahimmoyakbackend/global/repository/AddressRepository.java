package com.example.ahimmoyakbackend.global.repository;

import com.example.ahimmoyakbackend.global.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByUserId(Long userId);
}