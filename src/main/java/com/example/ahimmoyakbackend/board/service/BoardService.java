package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.board.dto.*;
import com.example.ahimmoyakbackend.board.entity.Board;
import com.example.ahimmoyakbackend.board.entity.Type;
import com.example.ahimmoyakbackend.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardCreateResponseDTO create(BoardCreateRequestDTO requestDTO, Type boardType) {
        Board board = Board.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .boardType(boardType)
                .build();
        boardRepository.save(board);
        return BoardCreateResponseDTO.builder().msg("게시물 작성 완료").build();
    }

    public BoardUpdateResponseDTO update(BoardUpdateRequestDTO requestDTO, Long boardId) {
        Board updated = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("없는 게시물 입니다."));
        updated.patch(requestDTO, boardId);
        boardRepository.save(updated);
        return BoardUpdateResponseDTO.builder().msg("게시물 수정 완료").build();
    }

    public BoardDeleteResponseDTO delete(Long boardId) {
        Board deleted = boardRepository.findById(boardId).orElseThrow(()->new IllegalArgumentException("없는 게시물 입니다."));
        boardRepository.delete(deleted);
        return BoardDeleteResponseDTO.builder().msg("게시물 삭제 완료").build();
    }

    public List<BoardResponseDTO> show(Type boardType) {
        return boardRepository.findAllByBoardType(boardType)
                .stream()
                .map(BoardResponseDTO::createDTO)
                .collect(Collectors.toList());
    }
}