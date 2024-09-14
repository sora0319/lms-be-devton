package com.example.ahimmoyakbackend.board.entity;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.dto.BoardUpdateRequestDto;
import com.example.ahimmoyakbackend.board.dto.CourseBoardsResponseDto;
import com.example.ahimmoyakbackend.course.entity.Course;
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
@Table(name = "course_board")
public class CourseBoard extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private BoardType type;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void patch(BoardUpdateRequestDto requestDTO, Long courseBoardId, Long courseId) {
        if(this.course.getId() != courseId || this.course.getId() != courseBoardId){
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

    public static CourseBoardsResponseDto toBoardResponseDto(CourseBoard courseBoard) {
        return new CourseBoardsResponseDto(
                courseBoard.getUser().getUsername(),
                courseBoard.getTitle(),
                courseBoard.getType(),
                courseBoard.getCreatedAt()
        );
    }
}