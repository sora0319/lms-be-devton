package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.dto.BoardCreateRequestDto;
import com.example.ahimmoyakbackend.board.dto.BoardListResponseDto;
import com.example.ahimmoyakbackend.board.dto.BoardPageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CourseBoardService {
    public boolean create(UserDetails userDetails, long courseId, BoardType boardType, BoardCreateRequestDto requestDto);
    public boolean update(UserDetails userDetails, long boardId, BoardCreateRequestDto requestDto);
    public boolean delete(UserDetails userDetails, long boardId);
    public List<BoardListResponseDto> getList(long courseId, BoardType boardType);
    public Page<BoardListResponseDto> getList(long courseId, BoardType boardType, Pageable pageable);
    public BoardPageResponseDto getBoard(long boardId);
}
