package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.course.entity.Contents;
import com.example.ahimmoyakbackend.course.entity.ContentsHistory;
import com.example.ahimmoyakbackend.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentsHistoryRepository extends JpaRepository<ContentsHistory, Long> {
    List<ContentsHistory> findByEnrollment_UserAndContents_Curriculum_Course(User user, Course course);

    Optional<ContentsHistory> findByContentsAndEnrollment_User(Contents contents, User user);
}
