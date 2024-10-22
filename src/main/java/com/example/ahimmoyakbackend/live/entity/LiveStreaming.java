package com.example.ahimmoyakbackend.live.entity;

import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import com.example.ahimmoyakbackend.live.common.LiveState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "live_streaming")
public class LiveStreaming extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    @Enumerated(EnumType.STRING)
    private LiveState state;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public void setState(LiveState state) {
        this.state = state;
    }

}