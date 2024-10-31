package com.example.ahimmoyakbackend.enrollment.service;

import com.example.ahimmoyakbackend.enrollment.dto.EnrollmentIdResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface EnrollmentService {
    public boolean make(UserDetails userDetails, long courseId);
    public boolean cancel(UserDetails userDetails, long enrollId);
    public boolean cancel(long courseId, UserDetails userDetails);
    public EnrollmentIdResponseDto getEnrollId(UserDetails userDetails, Long courseId);
}
