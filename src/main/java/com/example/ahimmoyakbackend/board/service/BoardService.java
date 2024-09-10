package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.board.dto.BoardCreateRequestDTO;
import com.example.ahimmoyakbackend.board.dto.BoardCreateResponseDTO;
import com.example.ahimmoyakbackend.board.entity.Board;
import com.example.ahimmoyakbackend.board.entity.Type;
import com.example.ahimmoyakbackend.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
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
}
