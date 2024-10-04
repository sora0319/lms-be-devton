package com.example.ahimmoyakbackend.company.entity;

import com.example.ahimmoyakbackend.global.entity.Timestamped;
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
@Table(name = "company")
public class Company extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String ownerName;

    @Column(nullable = false)
    private String businessNumber;

    @Column(length = 100)
    private String email;

    @Column(length = 50, nullable = false)
    private String emailDomain;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "id")
    private List<CourseProvide> courseProvide;
}
