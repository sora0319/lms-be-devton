package com.example.ahimmoyakbackend.live.service;

import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.AttendHistoryRepository;
import com.example.ahimmoyakbackend.course.repository.EnrollmentRepository;
import com.example.ahimmoyakbackend.live.dto.ChatMessagePubDto;
import com.example.ahimmoyakbackend.live.dto.ChatMessageSubDto;
import com.example.ahimmoyakbackend.live.dto.LiveJoinDto;
import com.example.ahimmoyakbackend.live.entity.AttendHistory;
import com.example.ahimmoyakbackend.live.entity.ChatAttend;
import com.example.ahimmoyakbackend.live.entity.ChatMessage;
import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import com.example.ahimmoyakbackend.live.repository.ChatAttendRepository;
import com.example.ahimmoyakbackend.live.repository.ChatMessageRepository;
import com.example.ahimmoyakbackend.live.repository.LiveStatusRepository;
import com.example.ahimmoyakbackend.live.repository.LiveStreamingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final LiveStatusRepository liveStatusRepository;
    private final ChatAttendRepository chatAttendRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LiveStreamingRepository liveStreamingRepository;
    private final AttendHistoryRepository attendHistoryRepository;

    @Transactional
    public ChatMessageSubDto message(long liveId, ChatMessagePubDto dto) {
        if (!liveStatusRepository.existsById(liveId)) {
            return null;
        }
        ChatMessage chatMessage = chatMessageRepository.save(dto.to(liveId));
        return ChatMessageSubDto.from(chatMessage);
    }

    @Transactional
    public List<ChatMessageSubDto> getAll(long liveId) {
        return chatMessageRepository.findAllByLiveIdOrderByTimeAsc(liveId).stream()
                .map(ChatMessageSubDto::from)
                .toList();
    }

    @Transactional
    public void deleteAll(long liveId) {
        List<ChatMessage> messages = chatMessageRepository.findAllByLiveIdOrderByTimeAsc(liveId);
        chatMessageRepository.deleteAll(messages);
    }

    @Transactional
    public void attend(long liveId, LiveJoinDto dto) {
        ChatAttend chatAttend = chatAttendRepository.findById(liveId).orElse(null);
        if(chatAttend == null) {return;}
        chatAttend.getUsers().add(dto.username());
        chatAttendRepository.save(chatAttend);
    }

    @Transactional
    public void saveAttendHistory(long liveId) {
        ChatAttend chatAttend = chatAttendRepository.findById(liveId).orElse(null);
        if(chatAttend == null) {return;}
        LiveStreaming live = liveStreamingRepository.findById(liveId).orElse(null);
        if(live == null) {return;}
        Course course = live.getCourse();
        attendHistoryRepository.saveAll(course.getEnrollments().stream()
                .map(enrollment -> AttendHistory.builder()
                        .enrollment(enrollment)
                        .liveStreaming(live)
                        .attendance(chatAttend.getUsers().contains(enrollment.getUser().getUsername()))
                        .build()).toList());
        chatAttendRepository.delete(chatAttend);
    }
}
