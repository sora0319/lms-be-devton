package com.example.ahimmoyakbackend.board.entity;


import com.example.ahimmoyakbackend.auth.entity.Timestamped;
import com.example.ahimmoyakbackend.auth.entity.User;
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
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 255)
    private String content;

    @Column(nullable = false, length = 255)
    @Enumerated(EnumType.STRING)
    private Type boardType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
