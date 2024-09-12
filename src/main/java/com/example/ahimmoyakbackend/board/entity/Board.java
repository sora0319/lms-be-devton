package com.example.ahimmoyakbackend.board.entity;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.dto.BoardResponseDTO;
import com.example.ahimmoyakbackend.board.dto.BoardUpdateRequestDTO;
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
@Table(name = "board")
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void patch(BoardUpdateRequestDTO requestDTO, Long boardId) {
        if(this.id != boardId){
           throw new IllegalArgumentException("잘못된 게시물 입니다.");
        }
        if(requestDTO.getTitle() != null){
            this.title = requestDTO.getTitle();
        }
        if(requestDTO.getContent() != null){
            this.content = requestDTO.getContent();
        }
        if(requestDTO.getType() != null){
            this.type = requestDTO.getType();
        }
    }

    public static BoardResponseDTO toDTO(Board board){
        return new BoardResponseDTO(board.getUser(),board.getTitle(), board.getContent(),board.getType(),board.getCreatedAt());
    }

}