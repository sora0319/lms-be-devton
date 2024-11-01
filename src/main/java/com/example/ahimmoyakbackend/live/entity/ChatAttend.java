package com.example.ahimmoyakbackend.live.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "attend")
public class ChatAttend {
    @Id
    private String id;
    @Indexed
    private long liveId;
    @Indexed
    private String username;
}
