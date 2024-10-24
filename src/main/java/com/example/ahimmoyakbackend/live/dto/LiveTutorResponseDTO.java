package com.example.ahimmoyakbackend.live.dto;

import com.example.ahimmoyakbackend.institution.entity.Tutor;
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
public class LiveTutorResponseDTO {
    private Long key;
    private String title;
    private String course;
    private String instructor;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LiveState state;
    private String streamKey;

    public static LiveTutorResponseDTO from(LiveStreaming liveStreaming, Tutor tutor) {
        return LiveTutorResponseDTO.builder()
                .key(liveStreaming.getId())
                .title(liveStreaming.getTitle())
                .course(liveStreaming.getCourseProvide().getCourse().getTitle())
                .instructor(tutor.getUser().getName())
                .startTime(liveStreaming.getStartTime())
                .endTime(liveStreaming.getEndTime())
                .state(liveStreaming.getState())
                .streamKey(liveStreaming.getId()+"_"+tutor.getId())
                .build();
    }
}
