package com.example.ahimmoyakbackend.live.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.live.common.LiveState;
import com.example.ahimmoyakbackend.live.dto.LiveCourseResponseDTO;
import com.example.ahimmoyakbackend.live.dto.LiveCreateRequestDTO;
import com.example.ahimmoyakbackend.live.dto.LiveTutorResponseDTO;
import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import com.example.ahimmoyakbackend.live.repository.LiveStreamingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveService {
    private final LiveStreamingRepository liveStreamingRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean createLive(LiveCreateRequestDTO requestDTO, Long courseId, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalUser.isEmpty() || optionalCourse.isEmpty()){
            return false;
        }
        User user = optionalUser.get();
        Course course = optionalCourse.get();
        if(!course.getTutor().equals(user)){
            return false;
        }
        liveStreamingRepository.save(requestDTO.toEntity(course));
        return true;
    }

    public LiveCourseResponseDTO getLive(Long liveId) {
        Optional<LiveStreaming> optionalLiveStreaming = liveStreamingRepository.findById(liveId);
        if(optionalLiveStreaming.isEmpty()) return null;
        LiveStreaming liveStreaming = optionalLiveStreaming.get();
        return LiveCourseResponseDTO.from(liveStreaming, liveStreaming.getCourse());
    }

    public List<LiveCourseResponseDTO> getLiveListByCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()){
            return new ArrayList<>();
        }
        Course course = optionalCourse.get();
        return liveStreamingRepository.findByCourse(course).stream()
                .map(entity -> LiveCourseResponseDTO.from(entity, course))
                .toList();

    }

    public List<LiveTutorResponseDTO> getLiveListByTutor(Long tutorId) {
        Optional<User> optionalTutor = userRepository.findById(tutorId);
        if(optionalTutor.isEmpty()){
            return new ArrayList<>();
        }
        User tutor = optionalTutor.get();
        return liveStreamingRepository.findByCourse_Tutor(tutor).stream()
                .map(entity -> LiveTutorResponseDTO.from(entity, tutor))
                .toList();
    }

    @Transactional
    public boolean publishLive(String streamKey) {
        String[] keyAndTutor = streamKey.split("_");
        Long liveId = Long.parseLong(keyAndTutor[0]);
        Long tutorId = Long.parseLong(keyAndTutor[1]);
        LiveStreaming liveStreaming = liveStreamingRepository.findById(liveId).orElse(null);
        User tutor = userRepository.findById(tutorId).orElse(null);
        if(liveStreaming != null && tutor != null){
            if(liveStreaming.getCourse().getTutor().equals(tutor)){
                liveStreaming.setState(LiveState.ON);
                log.info("Publish live streaming success. stream_key: {}, tutor_id: {}", liveId, tutorId);
                return true;
            }
        }
        log.error("Publish live streaming failed. stream_key: {}, tutor_id: {}", liveId, tutorId);
        return false;
    }

    @Transactional
    public void endLive(String streamKey) {
        String[] keyAndTutor = streamKey.split("_");
        Long liveId = Long.parseLong(keyAndTutor[0]);
        liveStreamingRepository.findById(liveId).ifPresent(liveStreaming -> liveStreaming.setState(LiveState.END));
        log.info("Close live streaming. stream_key: {}, tutor_id: {}", liveId, keyAndTutor[1]);
    }
}
