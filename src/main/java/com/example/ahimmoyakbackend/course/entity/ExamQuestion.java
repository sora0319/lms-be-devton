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
@Table(name = "exam_question")
public class ExamQuestion extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String question;

    @Column(nullable = false)
    private Integer answer;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

}