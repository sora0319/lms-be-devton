package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.auth.common.UserRole;
import com.example.ahimmoyakbackend.auth.entity.User;
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

    public BoardCreateResponseDto create(User user, BoardCreateRequestDto requestDTO, BoardType type) {
        if (type.equals(BoardType.NOTICE) && user.getRole().equals(UserRole.ADMIN)) {
            String msg = createBoard(requestDTO,type);
            return BoardCreateResponseDto.builder().msg(msg).build();
        } else if (type.equals(BoardType.QNA)) {
            String msg = createBoard(requestDTO,type);
            return BoardCreateResponseDto.builder().msg(msg).build();
        } else {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
    }

    public BoardUpdateResponseDto update(User user, BoardUpdateRequestDto requestDTO, Long boardId) {
        Board updated = boardRepository.findByIdAndUser(boardId,user);
        if (updated.getType().equals(BoardType.NOTICE) && user.getRole().equals(UserRole.ADMIN)) {
            updated.patch(requestDTO, boardId);
            boardRepository.save(updated);
            return BoardUpdateResponseDto.builder().msg("공지 게시물 수정 완료").build();
        } else if (updated.getType().equals(BoardType.QNA)) {
            updated.patch(requestDTO, boardId);
            boardRepository.save(updated);
            return BoardUpdateResponseDto.builder().msg("게시물 수정 완료").build();
        } else {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
    }

    public BoardDeleteResponseDto delete(User user, Long boardId) {
        Board deleted = boardRepository.findByIdAndUser(boardId,user);
        if (deleted.getType().equals(BoardType.NOTICE) && user.getRole().equals(UserRole.ADMIN)) {
            boardRepository.delete(deleted);
            return BoardDeleteResponseDto.builder().msg("게시물 삭제 완료").build();
        }else if (deleted.getType().equals(BoardType.QNA)) {
            boardRepository.delete(deleted);
            return BoardDeleteResponseDto.builder().msg("게시물 삭제 완료").build();
        }else {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
    }

    public Page<BoardInquiryResponseDto> inquiry(BoardType type, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return boardRepository.findAllByTypeOrderByCreatedAtDesc(type, pageable).map(Board::toDto);
    }

    public Page<BoardInquiryResponseDto> inquiryCreatedBoard(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return boardRepository.findAllByUserOrderByCreatedAtDesc(user, pageable).map(Board::toDto);
    }

    public BoardShowResponseDto show(BoardType type, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("없는 게시물 입니다."));
        return BoardShowResponseDto.builder()
                .username(board.getUser().getUsername())
                .title(board.getTitle())
                .content(board.getContent())
                .type(type)
                .boardId(board.getId())
                .createAt(board.getCreatedAt())
                .comments(commentRepository.findAllByBoardId(boardId).stream().map(Comment::toDto).toList())
                .build();
    }

    public String createBoard(BoardCreateRequestDto requestDTO, BoardType type){
        Board board = Board.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .type(type)
                .build();
        boardRepository.save(board);
        return type+"게시글 작성 완료";
    }

}