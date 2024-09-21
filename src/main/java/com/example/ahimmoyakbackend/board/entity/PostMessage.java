package com.example.ahimmoyakbackend.board.entity;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.board.dto.PostMessageResponseDto;
import com.example.ahimmoyakbackend.board.dto.PostMessageShowResponseDto;
import com.example.ahimmoyakbackend.board.dto.ReceivePostMessageResponseDto;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "postMessage", orphanRemoval = true)
    private List<TargetUser> targetUsers = new ArrayList<>();


    public static PostMessageResponseDto toDto(PostMessage postMessage) {
        List<Boolean> readMessageBool = postMessage.getTargetUsers().stream().map(TargetUser::getIsRead).toList();
        Boolean readMessage = false;
        for(Boolean bool:readMessageBool){
            if(!bool){
                readMessage =false;
                break;
            }else readMessage=true;
        }
        return PostMessageResponseDto.builder()
                .title(postMessage.getTitle())
                .content(postMessage.getContent())
                .senderName(postMessage.getUser().getUsername())
                .receiverName(postMessage.getTargetUsers().stream().map(user->user.getUser().getUsername()).collect(Collectors.toList()))
                .isRead(readMessage)
                .createAt(postMessage.getCreatedAt())
                .build();
    }
    public static PostMessageShowResponseDto toReadDto(PostMessage postMessage, TargetUser targetUser) {
        return PostMessageShowResponseDto.builder()
                .title(postMessage.getTitle())
                .content(postMessage.getContent())
                .senderName(postMessage.getUser().getUsername())
                .receiverName(targetUser.getUser().getUsername())
                .createAt(postMessage.getCreatedAt())
                .isRead(targetUser.getIsRead())
                .build();
    }

    public static ReceivePostMessageResponseDto toReceiveMessageDto(PostMessage postMessage) {
        return ReceivePostMessageResponseDto.builder()
                .title(postMessage.getTitle())
                .content(postMessage.getContent())
                .senderName(postMessage.getUser().getUsername())
                .createAt(postMessage.getCreatedAt())
                .build();
    }


}