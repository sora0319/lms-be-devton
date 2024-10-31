package com.example.ahimmoyakbackend.live.repository;

import com.example.ahimmoyakbackend.live.entity.ChatAttend;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatAttendRepository extends CrudRepository<ChatAttend, Long> {

}
