package com.example.ahimmoyakbackend.live.dto;

import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.live.common.LiveState;
import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LiveCreateRequestDTO {
    private String title;
    private LocalDateTime startTime;

    public LiveStreaming toEntity(CourseProvide courseProvide) {
        return LiveStreaming.builder()
                .title(title)
                .startTime(startTime)
                .endTime(null)
                .state(LiveState.CREATED)
                .courseProvide(courseProvide)
                .build();
    }
}
