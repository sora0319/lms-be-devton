package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.board.dto.*;
import com.example.ahimmoyakbackend.board.entity.PostMessage;
import com.example.ahimmoyakbackend.board.entity.TargetUser;
import com.example.ahimmoyakbackend.board.repository.PostMessageRepository;
import com.example.ahimmoyakbackend.board.repository.TargetUserRepository;
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
    private final TargetUserRepository targetUserRepository;

    public SendPostMessageResponseDto write(SendPostMessageRequestDto messageRequestDto) {
        User user1 = userRepository.findUserByUsername(messageRequestDto.getSender()).orElseThrow(()->new IllegalArgumentException("잘못된 유저"));
        PostMessage message = PostMessage.builder()
                .title(messageRequestDto.getTitle())
                .content(messageRequestDto.getContent())
                .user(user1)
                .build();
        postMessageRepository.save(message);

        List<User> names = messageRequestDto.getTargetNames().stream().map(targetUserDto->userRepository.findUserByUsername(targetUserDto.getTargetUsername()).orElseThrow(()->new IllegalArgumentException("잘못된 사용자입니다."))).toList();
        for(User user : names){
            targetUserRepository.save(TargetUser.builder().postMessage(message).isRead(false).user(user).build());
        }
        return SendPostMessageResponseDto.builder().msg("메세지 전송").build();
    }

    public PostMessageInquiryResponseDto sendInquriy(Long id, User user, int page, int size) {
//        User target = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("잘못된 유저입니다."));
        User target = userRepository.findById(id).orElseThrow();
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PostMessage> messagesPages = postMessageRepository
                .findByUserAndIsDeleteFalseOrderByCreatedAtDesc(target, pageable);
        List<PostMessageResponseDto> messages = messagesPages
                .stream()
                .map(PostMessage::toDto)
                .collect(Collectors.toList());
        return new PostMessageInquiryResponseDto(
                messages,
                new Pagination(page, size)
        );
    }

    public ReceivePostMessageInquiryResponseDto receiveInquriy(Long id, User user, int page, int size) {
//        User target = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("잘못된 유저입니다."));
        User target = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("잘못된 유저입니다."));
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<PostMessage> messagesPages = postMessageRepository
                .findByTargetUsers_UserAndTargetUsers_IsDeleteFalseOrderByCreatedAtDesc(target, pageable);

        List<ReceivePostMessageResponseDto> messages = messagesPages
                .stream()
                .map(PostMessage::toReceiveMessageDto)
                .collect(Collectors.toList());

        return new ReceivePostMessageInquiryResponseDto(
                messages,
                new Pagination(page, size)
        );
    }

    public PostMessageResponseDto showSendMessage(Long id, User user, Long messageId) {
//        User user1 = userRepository.findById(user.getId()).orElseThrow(()-> new IllegalArgumentException("잘못된 유저입니다."));
        User user1 = userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("잘못된 유저입니다."));
        PostMessage target = postMessageRepository.findByIdAndUser(messageId,user1);
        return PostMessage.toDto(target);
    }

    public PostMessageShowResponseDto showReceiveMessage(Long id, User user, Long messageId) {
//        User user1 = userRepository.findById(user.getId()).orElseThrow(()-> new IllegalArgumentException("잘못된 유저입니다."));
        User user1 = userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("잘못된 유저입니다."));
        PostMessage target = postMessageRepository.findById(messageId).orElseThrow(() -> new IllegalArgumentException("잘못된 쪽지 입니다."));
        TargetUser targetUser = targetUserRepository.findTargetUserByPostMessageAndUser(target,user1);
        targetUser.read();
        targetUserRepository.save(targetUser);
        return PostMessage.toReadDto(target,targetUser);
    }

    public DeletePostMessageResponseDto deleteSendMessage(Long id,User user,Long messageId) {
        User user1 = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("잘못된 유저입니다."));
        PostMessage target = postMessageRepository.findByIdAndUser(messageId,user1);
        target.delete();
        postMessageRepository.save(target);
        return DeletePostMessageResponseDto.builder().msg("메세지 삭제").build();
    }

    public DeletePostMessageResponseDto deleteReceiveMessage(Long id,User user,Long messageId) {
        User user1 = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("잘못된 유저입니다."));
        PostMessage message = postMessageRepository.findById(messageId).orElseThrow(()->new IllegalArgumentException("잘못된 쪽지 입니다."));
        TargetUser target = targetUserRepository.findTargetUserByPostMessageAndUser(message,user1);
        target.delete();
        targetUserRepository.save(target);
        return DeletePostMessageResponseDto.builder().msg("메세지 삭제").build();
    }
}
