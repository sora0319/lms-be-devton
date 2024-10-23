package com.example.ahimmoyakbackend.auth.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    List<User> findAllByAffiliation_Department_Company_Id(Long id);

    boolean existsByUsername(String username);
}
