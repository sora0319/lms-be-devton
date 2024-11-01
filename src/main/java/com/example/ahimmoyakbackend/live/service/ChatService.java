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
import java.util.UUID;

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
        if(!liveStatusRepository.existsById(liveId)){return;}
        ChatAttend chatAttend = chatAttendRepository.findByLiveIdAndUsername(liveId, dto.username());
        if(chatAttend != null) {return;}
        chatAttendRepository.save(ChatAttend.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .liveId(liveId)
                .username(dto.username())
                .build());
    }

    @Transactional
    public void saveAttendHistory(long liveId) {
        List<ChatAttend> chatAttends = chatAttendRepository.findAllByLiveId(liveId);
        LiveStreaming live = liveStreamingRepository.findById(liveId).orElse(null);
        if(live == null) {return;}
        Course course = live.getCourse();
        attendHistoryRepository.saveAll(chatAttends.stream()
                .map(attend -> AttendHistory.builder()
                        .enrollment(enrollmentRepository.findByUser_UsernameAndCourse(attend.getUsername(), course))
                        .liveStreaming(live)
                        .attendance(true)
                        .build()).toList());
        chatAttendRepository.deleteAll(chatAttends);
    }
}
