package com.example.ahimmoyakbackend;

import com.example.ahimmoyakbackend.auth.common.Gender;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.board.repository.CourseBoardRepository;
import com.example.ahimmoyakbackend.board.repository.CourseCommentRepository;
import com.example.ahimmoyakbackend.course.common.CourseState;
import com.example.ahimmoyakbackend.course.common.EnrollmentState;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import com.example.ahimmoyakbackend.course.entity.Enrollment;
import com.example.ahimmoyakbackend.course.repository.*;
import com.example.ahimmoyakbackend.live.repository.LiveQuizAnswerRepository;
import com.example.ahimmoyakbackend.live.repository.LiveQuizOptionRepository;
import com.example.ahimmoyakbackend.live.repository.LiveQuizRepository;
import com.example.ahimmoyakbackend.live.repository.LiveStreamingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

/**
 * DB 에 작업과 테스트에 필요한 데이터들을 생성해주는 코드입니다.
 * start 메소드를 실행하면 자동으로 데이터베이스에 데이터가 생성됩니다.
 * (테스트용으로 작성된 테스트 코드가 아님 주의!)
 *
 * [...딸깍!]
 */
@SpringBootTest
public class DataGenerate {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 이 메소드를 실행하면 데이터가 생성 됩니다.
     */
    @Test
    void start() {part1();}

    /*
    생성되는 데이터
    유저1
    유저2
    유저3
    코스1 (교육기관1, 유저1)
    커리큘럼1,2 (코스1)
    인롤먼트1 (유저2, 컨트랙트1)
    인롤먼트2 (유저3, 컨트랙트2)
     */

    @Autowired UserRepository userRepository;
    @Autowired CourseBoardRepository courseBoardRepository;
    @Autowired CurriculumRepository curriculumRepository;
    @Autowired CourseCommentRepository courseCommentRepository;
    @Autowired AttendHistoryRepository attendHistoryRepository;
    @Autowired ContentsHistoryRepository contentsHistoryRepository;
    @Autowired ContentsMaterialRepository contentsMaterialRepository;
    @Autowired ContentsRepository contentsRepository;
    @Autowired ContentsVideoRepository contentsVideoRepository;
    @Autowired CourseRepository courseRepository;
    @Autowired CurriculumRepository CurriculumRepository;
    @Autowired EnrollmentRepository enrollmentRepository;
    @Autowired LiveQuizAnswerRepository liveQuizAnswerRepository;
    @Autowired LiveQuizOptionRepository liveQuizOptionRepository;
    @Autowired LiveQuizRepository liveQuizRepository;
    @Autowired LiveStreamingRepository liveStreamingRepository;

    void part1() {

        // User (Supervisor) 생성
        User user1 = User.builder()
                .username("1111")
                .name("일일일")
                .password(passwordEncoder.encode("1111"))
                .birth(LocalDate.of(1993, 3, 3))
                .phone("01011111111")
                .email("one@ahim.com")
                .gender(Gender.MALE)
                .build();
        userRepository.save(user1);

        // User (Manager) 생성
        User user2 = User.builder()
                .username("2222")
                .name("둘둘둘")
                .password(passwordEncoder.encode("2222"))
                .birth(LocalDate.of(1993, 3, 3))
                .phone("01022222222")
                .email("two@moyak.com")
                .gender(Gender.MALE)
                .build();
        userRepository.save(user2);

        // Tutor 생성
        User user3 = User.builder()
                .username("3333")
                .name("삼삼삼")
                .password(passwordEncoder.encode("3333"))
                .birth(LocalDate.of(1993, 3, 3))
                .phone("01033333333")
                .email("three@tutor.com")
                .gender(Gender.MALE)
                .build();
        userRepository.save(user3);

        // Course 생성
        Course course1 = Course.builder()
                .tutor(user1)
                .title("코스1: 재밌는자바배우기")
                .introduction("자바에대해 배워봅시다")
                .state(CourseState.ONGOING)
                .beginDate(LocalDate.of(2024, 10,20))
                .endDate(LocalDate.of(2024, 12,31))
                .build();
        courseRepository.save(course1);

        // Curriculum 들 생성
        Curriculum curriculum1 = Curriculum.builder()
                .course(course1)
                .title("자바기초")
                .idx(1)
                .build();
        curriculumRepository.save(curriculum1);
        Curriculum curriculum2 = Curriculum.builder()
                .course(course1)
                .title("자바심화")
                .idx(2)
                .build();
        curriculumRepository.save(curriculum2);


        // Contract 와 수강생들 매핑하는 Enrollment 생성
        Enrollment enrollment1 = Enrollment.builder()
                .user(user2)
                .course(course1)
                .state(EnrollmentState.ONPROGRESS)
                .certificateDate(null)
                .build();
        enrollmentRepository.save(enrollment1);
        Enrollment enrollment2 = Enrollment.builder()
                .user(user3)
                .course(course1)
                .state(EnrollmentState.ONPROGRESS)
                .certificateDate(null)
                .build();
        enrollmentRepository.save(enrollment2);

    }
}
