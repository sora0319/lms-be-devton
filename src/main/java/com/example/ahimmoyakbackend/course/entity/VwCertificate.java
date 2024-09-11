package com.example.ahimmoyakbackend.course.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Immutable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vw_certificate")
public class VwCertificate {
    @Id
    @Column(name = "id", nullable = false)
    private Long certificateNumber;

    @Column
    private String name;

    @Column
    private String company;

    @Column
    private String institution;

    @Column
    private String course;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

}