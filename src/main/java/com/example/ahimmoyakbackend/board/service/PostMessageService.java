package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.board.dto.PostMessageRequestDto;
import com.example.ahimmoyakbackend.board.dto.PostMessageResponseDto;
import com.example.ahimmoyakbackend.board.entity.PostMessage;
import com.example.ahimmoyakbackend.board.repository.PostMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostMessageService {

    private final UserRepository userRepository;
    private final PostMessageRepository postMessageRepository;

    public PostMessageResponseDto write(PostMessageRequestDto messageRequestDto) {

        User sender = userRepository.findUserByUsername(messageRequestDto.getSenderName()).orElseThrow( () -> new IllegalArgumentException("잘못된 유저입니다."));
        User receiver = userRepository.findUserByUsername(messageRequestDto.getReceiverName()).orElseThrow( () -> new IllegalArgumentException("잘못된 유저입니다."));

        PostMessage message = PostMessage.builder()
                .title(messageRequestDto.getTitle())
                .content(messageRequestDto.getContent())
                .sender(sender)
                .receiver(receiver)
                .build();
        postMessageRepository.save(message);
        return PostMessageResponseDto.builder().msg("메세지 전송").build();
    }
}
