package com.example.ahimmoyakbackend.board.dto;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.entity.Board;
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

    private BoardType type;

    public static BoardResponseDTO createDTO(Board board){
        return new BoardResponseDTO(board.getUser(),board.getTitle(), board.getContent(),board.getType());
    }

}
