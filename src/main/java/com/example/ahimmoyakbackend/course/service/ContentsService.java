package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.course.dto.ContentsCreateRequestDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface ContentsService {
    public boolean addVideo(UserDetails userDetails, long curriculumId, ContentsCreateRequestDto requestDto);
}
