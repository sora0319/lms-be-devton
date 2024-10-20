package com.example.ahimmoyakbackend.board.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.entity.PostMessage;
import com.example.ahimmoyakbackend.board.entity.TargetUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetUserRepository extends JpaRepository<TargetUser, Long> {
    TargetUser findTargetUserByPostMessageAndUser(PostMessage postMessage,User user);
}