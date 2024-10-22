package com.example.ahimmoyakbackend.institution.entity;

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
@Table(name = "tutor")
public class Tutor extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String introduction;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

}
