package com.example.ahimmoyakbackend.company.entity;

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
@Table(name = "affiliation")
public class Affiliation extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    @Column(nullable = false)
    private Boolean isSupervisor;

//    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    @Column(nullable = false)
    private Boolean approval;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(mappedBy = "affiliation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private User user;
}
