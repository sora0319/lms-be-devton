package com.example.ahimmoyakbackend.course.entity;

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
@Table(name = "quiz_option")
public class QuizOption extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @Column(nullable = false)
    private Integer idx;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private ContentsQuiz contentsQuiz;

}