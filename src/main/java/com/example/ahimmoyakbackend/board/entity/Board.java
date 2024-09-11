package com.example.ahimmoyakbackend.board.entity;


import com.example.ahimmoyakbackend.auth.entity.Timestamped;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.dto.BoardUpdateRequestDTO;
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

    public void patch(BoardUpdateRequestDTO requestDTO,Long id) {
        if(this.id != id){
            throw new IllegalArgumentException("잘못된 게시물 입니다.");
        }
        if(requestDTO.getTitle() != null){
            this.title = requestDTO.getTitle();
        }
        if(requestDTO.getContent() != null){
            this.content = requestDTO.getContent();
        }
        if(requestDTO.getBoardType() != null){
            this.boardType = requestDTO.getBoardType();
        }
    }
}
