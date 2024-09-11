package com.example.ahimmoyakbackend.course.entity;

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
@Table(name = "contents_material")
public class ContentsMaterial extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String originName;

    @Column(nullable = false)
    private String savedName;

    @Column(nullable = false)
    private String postfix;

    @OneToOne
    @JoinColumn(name = "contents_id")
    private Contents contents;

}