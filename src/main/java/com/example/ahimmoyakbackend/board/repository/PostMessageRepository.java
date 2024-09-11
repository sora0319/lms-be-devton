package com.example.ahimmoyakbackend.board.repository;

import com.example.ahimmoyakbackend.board.entity.PostMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostMessageRepository extends JpaRepository<PostMessage, Long> {
}