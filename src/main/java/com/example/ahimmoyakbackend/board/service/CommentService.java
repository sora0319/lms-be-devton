package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.board.dto.CommentCreateRequestDto;
import com.example.ahimmoyakbackend.board.dto.CommentCreateResponseDto;
import com.example.ahimmoyakbackend.board.entity.Board;
import com.example.ahimmoyakbackend.board.entity.Comment;
import com.example.ahimmoyakbackend.board.repository.BoardRepository;
import com.example.ahimmoyakbackend.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public CommentCreateResponseDto create(CommentCreateRequestDto requestDto, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("없는 게시물 입니다."));
        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .board(board)
                .build();
        commentRepository.save(comment);
        return CommentCreateResponseDto.builder().msg("댓글 작성 성공").build();

    }

}
