package com.example.ahimmoyakbackend.live.entity;

import com.example.ahimmoyakbackend.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "live_quiz_option")
public class LiveQuizOption extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @Column(nullable = false)
    private Integer idx;

    @ManyToOne
    @JoinColumn(name = "live_quiz_id")
    private LiveQuiz liveQuiz;

}