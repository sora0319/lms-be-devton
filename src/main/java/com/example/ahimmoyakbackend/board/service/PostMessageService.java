package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.board.dto.*;
import com.example.ahimmoyakbackend.board.entity.PostMessage;
import com.example.ahimmoyakbackend.board.repository.PostMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostMessageService {

    private final UserRepository userRepository;
    private final PostMessageRepository postMessageRepository;

    public SendPostMessageResponseDto write(SendPostMessageRequestDto messageRequestDto) {

        User sender = userRepository.findUserByUsername(messageRequestDto.getSenderName()).orElseThrow( () -> new IllegalArgumentException("잘못된 유저입니다."));
        User receiver = userRepository.findUserByUsername(messageRequestDto.getReceiverName()).orElseThrow( () -> new IllegalArgumentException("잘못된 유저입니다."));

        PostMessage message = PostMessage.builder()
                .title(messageRequestDto.getTitle())
                .content(messageRequestDto.getContent())
                .sender(sender)
                .receiver(receiver)
                .isRead(false)
                .build();
        postMessageRepository.save(message);
        return SendPostMessageResponseDto.builder().msg("메세지 전송").build();
    }

    public PostMessageInquiryResponseDto sendInquriy(User user, int page, int size) {
        User target = userRepository.findById(user.getId()).orElseThrow(()->new IllegalArgumentException("잘못된 유저입니다."));
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PostMessage> messagesPages = postMessageRepository
                .findPostMessagesBySenderOrderByCreatedAtDesc(target, pageable);

        List<PostMessageResponseDto> messages = messagesPages
                .stream()
                .map(PostMessage::toDto)
                .collect(Collectors.toList());

        return new PostMessageInquiryResponseDto(
                messages,
                new Pagination(page,size)
        );
    }

    public PostMessageInquiryResponseDto receiveInquriy(User user, int page, int size) {
        User target = userRepository.findById(user.getId()).orElseThrow(()->new IllegalArgumentException("잘못된 유저입니다."));
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PostMessage> messagesPages = postMessageRepository
                .findPostMessagesByReceiverOrderByCreatedAtDesc(target, pageable);

        List<PostMessageResponseDto> messages = messagesPages
                .stream()
                .map(PostMessage::toDto)
                .collect(Collectors.toList());

        return new PostMessageInquiryResponseDto(
                messages,
                new Pagination(page,size)
        );
    }

}
