package com.example.ahimmoyakbackend.board.entity;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.dto.PostMessageResponseDto;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_message")
public class PostMessage extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column(name = "is_read")
    @ColumnDefault("false")
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    public static PostMessageResponseDto toDto(PostMessage postMessage) {
        return PostMessageResponseDto.builder()
                .title(postMessage.getTitle())
                .content(postMessage.getContent())
                .senderName(postMessage.getSender().getUsername())
                .receiverName(postMessage.getReceiver().getUsername())
                .isRead(postMessage.getIsRead())
                .build();
    }
}