package com.example.ahimmoyakbackend.board.entity;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "target_user")
public class TargetUser extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isRead;

    @Column
    @ColumnDefault("false")
    private Boolean isDelete = false;

    @ManyToOne
    @JoinColumn(name = "post_message_id")
    private PostMessage postMessage;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void read(){
        this.isRead=true;
    }

    public void delete() {
        this.isDelete = true;
    }
}