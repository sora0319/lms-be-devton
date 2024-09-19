package com.example.ahimmoyakbackend.live.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.institution.entity.Manager;
import com.example.ahimmoyakbackend.institution.entity.Tutor;
import com.example.ahimmoyakbackend.institution.repository.ManagerRepository;
import com.example.ahimmoyakbackend.institution.repository.TutorRepository;
import com.example.ahimmoyakbackend.live.common.LiveState;
import com.example.ahimmoyakbackend.live.dto.LiveCourseResponseDTO;
import com.example.ahimmoyakbackend.live.dto.LiveCreateRequestDTO;
import com.example.ahimmoyakbackend.live.dto.LivePublishFormDTO;
import com.example.ahimmoyakbackend.live.dto.LiveTutorResponseDTO;
import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import com.example.ahimmoyakbackend.live.repository.LiveStreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LiveService {
    private final LiveStreamingRepository liveStreamingRepository;
    private final ManagerRepository managerRepository;
    private final TutorRepository tutorRepository;
    private final CourseRepository courseRepository;

    public boolean createLive(LiveCreateRequestDTO requestDTO, Long courseId, String username) {
        Optional<Manager> optionalManager = managerRepository.findByUser_Username(username);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalManager.isEmpty() || optionalCourse.isEmpty()){
            return false;
        }
        Manager manager = optionalManager.get();
        Course course = optionalCourse.get();
        if(!course.getInstitution().equals(manager.getInstitution())){
            return false;
        }
        liveStreamingRepository.save(requestDTO.toEntity(course));
        return true;
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
        Optional<Tutor> optionalTutor = tutorRepository.findById(tutorId);
        if(optionalTutor.isEmpty()){
            return new ArrayList<>();
        }
        Tutor tutor = optionalTutor.get();
        return liveStreamingRepository.findByCourse_Tutor(tutor).stream()
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
            if(liveStreaming.getCourse().getTutor().equals(tutor)){
                liveStreaming.setState(LiveState.ON);
                return true;
            }
        }
        return false;
    }
}
