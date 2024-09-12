package com.example.ahimmoyakbackend.board.dto;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardShowResponseDTO {

    private String username;

    private String title;

    private String content;

    private BoardType type;

    private LocalDateTime createAt;

    private List<CommentOnBoardResponseDto> comments;

}
