package com.example.ahimmoyakbackend.enrollment.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface EnrollmentService {
    public boolean make(UserDetails userDetails, long courseId);
    public boolean cancel(UserDetails userDetails, long enrollId);
    public boolean cancel(long courseId, UserDetails userDetails);
}
