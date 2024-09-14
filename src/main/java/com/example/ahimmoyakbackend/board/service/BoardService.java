package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.dto.*;
import com.example.ahimmoyakbackend.board.entity.Board;
import com.example.ahimmoyakbackend.board.entity.Comment;
import com.example.ahimmoyakbackend.board.repository.BoardRepository;
import com.example.ahimmoyakbackend.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public BoardCreateResponseDto create(BoardCreateRequestDto requestDTO, BoardType type) {
        Board board = Board.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .type(type)
                .build();
        boardRepository.save(board);
        return BoardCreateResponseDto.builder().msg("게시물 작성 완료").build();
    }

    public BoardUpdateResponseDto update(BoardUpdateRequestDto requestDTO, Long boardId) {
        Board updated = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("없는 게시물 입니다."));
        updated.patch(requestDTO, boardId);
        boardRepository.save(updated);
        return BoardUpdateResponseDto.builder().msg("게시물 수정 완료").build();
    }

    public BoardDeleteResponseDto delete(Long boardId) {
        Board deleted = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("없는 게시물 입니다."));
        boardRepository.delete(deleted);
        return BoardDeleteResponseDto.builder().msg("게시물 삭제 완료").build();
    }

    public Page<BoardInquiryResponseDto> inquiry(BoardType type, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return boardRepository.findAllByTypeOrderByCreatedAtDesc(type, pageable).map(Board::toDto);
    }

    public BoardShowResponseDto show(BoardType type, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("없는 게시물 입니다."));
        return BoardShowResponseDto.builder()
                .username(board.getUser().getUsername())
                .title(board.getTitle())
                .content(board.getContent())
                .type(type)
                .createAt(board.getCreatedAt())
                .comments(commentRepository.findAllByBoardId(boardId).stream().map(Comment::toDto).toList())
                .build();
    }
}