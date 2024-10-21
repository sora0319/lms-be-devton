package com.example.ahimmoyakbackend.live.service;

import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import com.example.ahimmoyakbackend.company.repository.CourseProvideRepository;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.institution.entity.Manager;
import com.example.ahimmoyakbackend.institution.entity.Tutor;
import com.example.ahimmoyakbackend.institution.repository.ManagerRepository;
import com.example.ahimmoyakbackend.institution.repository.TutorRepository;
import com.example.ahimmoyakbackend.live.common.LiveState;
import com.example.ahimmoyakbackend.live.dto.LiveCourseResponseDTO;
import com.example.ahimmoyakbackend.live.dto.LiveCreateRequestDTO;
import com.example.ahimmoyakbackend.live.dto.LiveTutorResponseDTO;
import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import com.example.ahimmoyakbackend.live.repository.LiveStreamingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveService {
    private final LiveStreamingRepository liveStreamingRepository;
    private final ManagerRepository managerRepository;
    private final TutorRepository tutorRepository;
    private final CourseRepository courseRepository;
    private final CourseProvideRepository courseProvideRepository;

    public boolean createLive(LiveCreateRequestDTO requestDTO, Long courseProvideId, String username) {
        Optional<Manager> optionalManager = managerRepository.findByUser_Username(username);
        Optional<CourseProvide> optionalCourseProvide = courseProvideRepository.findById(courseProvideId);
        if(optionalManager.isEmpty() || optionalCourseProvide.isEmpty()){
            return false;
        }
        Manager manager = optionalManager.get();
        CourseProvide courseProvide = optionalCourseProvide.get();
        if(!courseProvide.getInstitution().equals(manager.getInstitution())){
            return false;
        }
        liveStreamingRepository.save(requestDTO.toEntity(courseProvide));
        return true;
    }

    public List<LiveCourseResponseDTO> getLiveListByCourse(Long courseProvideId) {
        Optional<CourseProvide> optionalCourseProvide = courseProvideRepository.findById(courseProvideId);
        if(optionalCourseProvide.isEmpty()){
            return new ArrayList<>();
        }
        CourseProvide courseProvide = optionalCourseProvide.get();
        return liveStreamingRepository.findByCourseProvide(courseProvide).stream()
                .map(entity -> LiveCourseResponseDTO.from(entity, courseProvide))
                .toList();

    }

    public List<LiveTutorResponseDTO> getLiveListByTutor(Long tutorId) {
        Optional<Tutor> optionalTutor = tutorRepository.findById(tutorId);
        if(optionalTutor.isEmpty()){
            return new ArrayList<>();
        }
        Tutor tutor = optionalTutor.get();
        return liveStreamingRepository.findByCourseProvide_Course_Tutor(tutor).stream()
                .map(entity -> LiveTutorResponseDTO.from(entity, tutor))
                .toList();
    }

    public boolean publishLive(String streamKey) {
        String[] keyAndTutor = streamKey.split("_");
        Long liveId = Long.parseLong(keyAndTutor[0]);
        Long tutorId = Long.parseLong(keyAndTutor[1]);
        LiveStreaming liveStreaming = liveStreamingRepository.findById(liveId).orElse(null);
        Tutor tutor = tutorRepository.findById(tutorId).orElse(null);
        if(liveStreaming != null && tutor != null){
            if(liveStreaming.getCourseProvide().getCourse().getTutor().equals(tutor)){
                liveStreaming.setState(LiveState.ON);
                log.info("Publish live streaming success. stream_key: {}, tutor_id: {}", liveId, tutorId);
                return true;
            }
        }
        log.error("Publish live streaming failed. stream_key: {}, tutor_id: {}", streamKey, tutorId);
        return false;
    }

    public void endLive(String streamKey) {
        String[] keyAndTutor = streamKey.split("_");
        Long liveId = Long.parseLong(keyAndTutor[0]);
        liveStreamingRepository.findById(liveId).ifPresent(liveStreaming -> liveStreaming.setState(LiveState.END));
        log.info("Close live streaming. stream_key: {}, tutor_id: {}", liveId, keyAndTutor[1]);
    }
}
