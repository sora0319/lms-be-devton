package com.example.ahimmoyakbackend.live.repository;

import com.example.ahimmoyakbackend.live.entity.ChatAttend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatAttendRepository extends CrudRepository<ChatAttend, Long> {

    List<ChatAttend> findAllByLiveId(long liveId);

    ChatAttend findByLiveIdAndUsername(long liveId, String username);

    boolean existsByLiveIdAndUsername(long liveId, String username);
}
