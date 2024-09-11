package com.example.ahimmoyakbackend.board.dto;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.entity.Board;
import com.example.ahimmoyakbackend.board.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDTO {

    private User users;

    private String title;

    private String content;

    private Type boardType;

    public static BoardResponseDTO createDTO(Board board){
        return new BoardResponseDTO(board.getUser(),board.getTitle(), board.getContent(),board.getBoardType());
    }

}
