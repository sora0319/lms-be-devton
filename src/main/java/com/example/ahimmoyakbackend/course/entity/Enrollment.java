package com.example.ahimmoyakbackend.course.entity;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import com.example.ahimmoyakbackend.course.common.EnrollmentState;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
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
@Table(name = "enrollment")
public class Enrollment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentState state;

    @Column
    private LocalDateTime certificateDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_Provide_id")
    private CourseProvide courseProvide;

    public void assignCourseProvide(CourseProvide courseProvide) {
        if (this.courseProvide != null) {
            throw new RuntimeException("계약 아이디가 없습니다.");
        }
        this.courseProvide = courseProvide;
    }
    public void cancelCourseProvide() {

        this.courseProvide = null;
    }



}

