package com.example.ahimmoyakbackend.course.entity;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.course.common.CourseState;
import com.example.ahimmoyakbackend.course.dto.CourseCreateRequestDto;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String introduction;

    @Column(nullable = false)
    private LocalDate beginDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CourseState state;

    @OneToMany(mappedBy = "course")
    private List<Curriculum> curriculumList;

    @Setter
    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private User tutor;

    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments = new ArrayList<>();

    public Course patch(CourseCreateRequestDto requestDto) {
        this.title = requestDto.title();
        this.introduction = requestDto.introduction();
        this.beginDate = requestDto.beginDate();
        this.endDate = requestDto.endDate();
        return this;
    }

    public Course setState(CourseState state) {
        this.state = state;
        return this;
    }
}
