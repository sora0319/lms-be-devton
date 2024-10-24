package com.example.ahimmoyakbackend.live.dto;

import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import com.example.ahimmoyakbackend.live.common.LiveState;
import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LiveCourseResponseDTO {
    private Long key;
    private String title;
    private String course;
    private Long courseId;
    private String instructor;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LiveState state;

    public static LiveCourseResponseDTO from(LiveStreaming liveStreaming, CourseProvide courseProvide) {
        return LiveCourseResponseDTO.builder()
                .key(liveStreaming.getId())
                .title(liveStreaming.getTitle())
                .course(courseProvide.getCourse().getTitle())
                .courseId(courseProvide.getCourse().getId())
                .instructor(courseProvide.getCourse().getTutor().getUser().getName())
                .startTime(liveStreaming.getStartTime())
                .endTime(liveStreaming.getEndTime())
                .state(liveStreaming.getState())
                .build();
    }
}
