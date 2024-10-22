package com.example.ahimmoyakbackend.institution.entity;

import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "institution")
public class Institution extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String ownerName;

    @Column(nullable = false)
    private String businessNumber;

    @Column(nullable = false)
    private String certifiedNumber;

    @Column
    private String email;

    @Column(length = 20)
    private String phone;

    @Builder.Default
    @OneToMany(mappedBy = "institution")
    private List<CourseProvide> courseProvide = new ArrayList<>();
}
