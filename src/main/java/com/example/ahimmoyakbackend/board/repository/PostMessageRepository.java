package com.example.ahimmoyakbackend.board.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.entity.PostMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostMessageRepository extends JpaRepository<PostMessage, Long> {
    Page<PostMessage> findPostMessagesBySenderOrderByCreatedAtDesc(User user, Pageable pageable);
}