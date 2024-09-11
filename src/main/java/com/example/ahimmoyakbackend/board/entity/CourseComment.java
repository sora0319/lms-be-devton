package com.example.ahimmoyakbackend.board.entity;

import com.example.ahimmoyakbackend.auth.entity.User;
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
@Table(name = "course_comment")
public class CourseComment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private CourseBoard courseBoard;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}