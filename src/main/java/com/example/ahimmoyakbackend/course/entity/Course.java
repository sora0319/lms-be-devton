package com.example.ahimmoyakbackend.course.entity;

import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.common.TrainingCourseType;
import com.example.ahimmoyakbackend.course.dto.CourseModifyRequestDTO;
import com.example.ahimmoyakbackend.global.entity.Image;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import com.example.ahimmoyakbackend.institution.entity.Tutor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Enumerated(EnumType.STRING)
    private CourseCategory category;

    @Column
    @Enumerated(EnumType.STRING)
    private TrainingCourseType type;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @OneToMany(mappedBy = "course")
    private List<CourseProvide> courseProvideList;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "course")
    private List<Curriculum> curriculumList;

    public Course patch(CourseModifyRequestDTO requestDTO) {

        if (requestDTO.getTitle() != null) {
            this.title = requestDTO.getTitle();
        }
        if (requestDTO.getIntroduction() != null) {
            this.introduction = requestDTO.getIntroduction();
        }
        if (requestDTO.getCategory() != null) {
            this.category = requestDTO.getCategory();
        }
        if (requestDTO.getImage() != null) {
            this.image = requestDTO.getImage();
        }
        if (requestDTO.getType() != null) {
            this.type = requestDTO.getType();
        }
        return this;
    }
}
