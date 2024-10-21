package com.example.ahimmoyakbackend.company.entity;

import com.example.ahimmoyakbackend.company.common.CourseProvideState;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.entity.Enrollment;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course_provide")
public class CourseProvide extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate beginDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
//    @ColumnDefault("PENDING")
    @Enumerated(value = EnumType.STRING)
    private CourseProvideState state;

    @Column(nullable = false)
    private Integer attendeeCount;

    @Column
    private Long deposit;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Affiliation supervisor;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "id")
    private List<Enrollment> enrollments = new ArrayList<>();

    public void patch(CourseProvideState state) {
        this.state = state;
    }
}