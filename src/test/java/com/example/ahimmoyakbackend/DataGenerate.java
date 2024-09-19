package com.example.ahimmoyakbackend;

import com.example.ahimmoyakbackend.auth.common.Gender;
import com.example.ahimmoyakbackend.auth.common.UserRole;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.board.repository.*;
import com.example.ahimmoyakbackend.company.common.ContractState;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.company.entity.Contract;
import com.example.ahimmoyakbackend.company.entity.Department;
import com.example.ahimmoyakbackend.company.repository.AffiliationRepository;
import com.example.ahimmoyakbackend.company.repository.CompanyRepository;
import com.example.ahimmoyakbackend.company.repository.ContractRepository;
import com.example.ahimmoyakbackend.company.repository.DepartmentRepository;
import com.example.ahimmoyakbackend.course.common.ContentType;
import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.common.EnrollmentState;
import com.example.ahimmoyakbackend.course.entity.*;
import com.example.ahimmoyakbackend.course.repository.*;
import com.example.ahimmoyakbackend.global.entity.Address;
import com.example.ahimmoyakbackend.global.repository.AddressRepository;
import com.example.ahimmoyakbackend.global.repository.ImageRepository;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import com.example.ahimmoyakbackend.institution.entity.Manager;
import com.example.ahimmoyakbackend.institution.entity.Tutor;
import com.example.ahimmoyakbackend.institution.repository.InstitutionRepository;
import com.example.ahimmoyakbackend.institution.repository.ManagerRepository;
import com.example.ahimmoyakbackend.institution.repository.TutorRepository;
import com.example.ahimmoyakbackend.live.repository.LiveQuizAnswerRepository;
import com.example.ahimmoyakbackend.live.repository.LiveQuizOptionRepository;
import com.example.ahimmoyakbackend.live.repository.LiveQuizRepository;
import com.example.ahimmoyakbackend.live.repository.LiveStreamingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    private CurriculumRepository curriculumRepository;

    /**
     * 이 메소드를 실행하면 데이터가 생성 됩니다.
     */
    @Test
    void start() {part1();}

    /*
    생성되는 데이터
    회사1
    부서1,2,3 (회사1)
    유저1
    어필레이션1 (유저1, 회사1-부서1)
    교육기관1
    유저2
    매니저1 (유저2, 교육기관1)
    유저3
    튜터1 (유저3)
    코스1 (교육기관1, 튜터1)
    커리큘럼1,2 (코스1)
    콘텐츠1,2 (커리큘럼1)
    콘텐츠3,4 (커리큘럼2)
    콘텐츠비디오1 (콘텐츠1)
    콘텐츠자료1 (콘텐츠2)
    콘텐츠비디오2 (콘텐츠3)
    콘텐츠자료2 (콘텐츠4)
    컨트랙트1 (회사1, 코스1)
    유저학생1,학생2
    어필레이션2 (유저학생1, 회사1-부서2)
    어필레이션3 (유저학생2, 회사1-부서3)
    인롤먼트1 (유저학생1, 컨트랙트1)
    인롤먼트2 (유저학생2, 컨트랙트2)




     */

    @Autowired UserRepository userRepository;
    @Autowired BoardRepository boardRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired CourseBoardRepository courseBoardRepository;
    @Autowired CourseCommentRepository courseCommentRepository;
    @Autowired PostMessageRepository postMessageRepository;
    @Autowired TargetUserRepository targetUserRepository;
    @Autowired AffiliationRepository affiliationRepository;
    @Autowired CompanyRepository companyRepository;
    @Autowired ContractRepository contractRepository;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired AttendHistoryRepository attendHistoryRepository;
    @Autowired ContentsHistoryRepository contentsHistoryRepository;
    @Autowired ContentsMaterialRepository contentsMaterialRepository;
    @Autowired ContentsQuizRepository contentsQuizRepository;
    @Autowired ContentsRepository contentsRepository;
    @Autowired ContentsVideoRepository contentsVideoRepository;
    @Autowired CourseRepository courseRepository;
    @Autowired CurriculumRepository CurriculumRepository;
    @Autowired EnrollmentRepository enrollmentRepository;
    @Autowired ExamOptionRepository examOptionRepository;
    @Autowired ExamQuestionRepository examQuestionRepository;
    @Autowired ExamRepository examRepository;
    @Autowired QuizOptionRepository quizOptionRepository;
    @Autowired UsersChoiceRepository UsersChoiceRepository;
    @Autowired AddressRepository addressRepository;
    @Autowired ImageRepository imageRepository;
    @Autowired InstitutionRepository institutionRepository;
    @Autowired ManagerRepository managerRepository;
    @Autowired TutorRepository tutorRepository;
    @Autowired LiveQuizAnswerRepository liveQuizAnswerRepository;
    @Autowired LiveQuizOptionRepository liveQuizOptionRepository;
    @Autowired LiveQuizRepository liveQuizRepository;
    @Autowired LiveStreamingRepository liveStreamingRepository;

    void part1() {
        // Company 생성
        Company company1 = Company.builder()
                .name("일번회사")
                .ownerName("이현규")
                .businessNumber("11111111")
                .email("contact@ahim.com")
                .emailDomain("ahim.com")
                .phone("051-1234-5678")
                .build();
        companyRepository.save(company1);

        // Department 생성
        Department department1 = Department.builder()
                .name("일번부서")
                .company(company1)
                .build();
        Department department2 = Department.builder()
                .name("이번부서")
                .company(company1)
                .build();
        Department department3 = Department.builder()
                .name("삼번부서")
                .company(company1)
                .build();
        departmentRepository.save(department1);
        departmentRepository.save(department2);
        departmentRepository.save(department3);

        // User (Supervisor) 생성
        User user1 = User.builder()
                .username("1111")
                .name("일일일")
                .password("1111")
                .birth(LocalDate.of(1993, 3, 3))
                .phone("01011111111")
                .email("one@ahim.com")
                .gender(Gender.MALE)
                .role(UserRole.EMPLOYEE)
                .build();
        userRepository.save(user1);
        Address address1 = Address.builder()
                .base("대충부산")
                .detail("진짜부산")
                .postal(11111)
                .user(user1)
                .company(null)
                .institution(null)
                .build();
        addressRepository.save(address1);

        // user1, company1 에 대한 Affiliation 생성
        Affiliation affiliation1 = Affiliation.builder()
                .department(department1)
                .user(user1)
                .isSupervisor(true)
                .approval(true)
                .build();
        affiliationRepository.save(affiliation1);

        // Institution 생성
        Institution institution1 = Institution.builder()
                .name("일번교육")
                .ownerName("삼현규")
                .businessNumber("10101010")
                .certifiedNumber("10101010")
                .email("contact@moyak.com")
                .phone("01010101010")
                .build();
        institutionRepository.save(institution1);
        Address address2 = Address.builder()
                .base("대충부산")
                .detail("진짜부산")
                .postal(11111)
                .user(null)
                .company(null)
                .institution(institution1)
                .build();
        addressRepository.save(address2);

        // User (Manager) 생성
        User user2 = User.builder()
                .username("2222")
                .name("둘둘둘")
                .password("2222")
                .birth(LocalDate.of(1993, 3, 3))
                .phone("01022222222")
                .email("two@moyak.com")
                .gender(Gender.MALE)
                .role(UserRole.MANAGER)
                .build();
        userRepository.save(user2);
        Manager manager1 = Manager.builder()
                .user(user2)
                .institution(institution1)
                .build();
        managerRepository.save(manager1);

        // Tutor 생성
        User user3 = User.builder()
                .username("3333")
                .name("삼삼삼")
                .password("3333")
                .birth(LocalDate.of(1993, 3, 3))
                .phone("01033333333")
                .email("three@tutor.com")
                .gender(Gender.MALE)
                .role(UserRole.TUTOR)
                .build();
        userRepository.save(user3);
        Tutor tutor1 = Tutor.builder()
                .user(user3)
                .build();
        tutorRepository.save(tutor1);

        // Course 생성
        Course course1 = Course.builder()
                .institution(institution1)
                .tutor(tutor1)
                .title("코스1: 재밌는자바배우기")
                .introduction("자바에대해 배워봅시다")
                .category(CourseCategory.INFORMATION_COMMUNICATION)
                .image(null)
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

        // Contents 들 생성 (커리1, 2 에 각각 영상 1개, 자료 1개씩)
        Contents contents1 = Contents.builder()
                .curriculum(curriculum1)
                .title("영상1")
                .type(ContentType.VIDEO)
                .idx(1)
                .build();
        contentsRepository.save(contents1);
        ContentsVideo contentsVideo1 = ContentsVideo.builder()
                .contents(contents1)
                .path("/video")
                .originName("test1")
                .savedName("1111")
                .postfix("mp4")
                .timeAmount(3L)
                .build();
        contentsVideoRepository.save(contentsVideo1);
        Contents contents2 = Contents.builder()
                .curriculum(curriculum1)
                .title("자료1")
                .type(ContentType.MATERIAL)
                .idx(2)
                .build();
        contentsRepository.save(contents2);
        ContentsMaterial contentsMaterial1 = ContentsMaterial.builder()
                .contents(contents1)
                .path("/material")
                .originName("test1")
                .savedName("1111")
                .postfix("pdf")
                .build();
        contentsMaterialRepository.save(contentsMaterial1);
        Contents contents3 = Contents.builder()
                .curriculum(curriculum2)
                .title("영상2")
                .type(ContentType.VIDEO)
                .idx(1)
                .build();
        contentsRepository.save(contents3);
        ContentsVideo contentsVideo2 = ContentsVideo.builder()
                .contents(contents3)
                .path("/video")
                .originName("test2")
                .savedName("2222")
                .postfix("mp4")
                .timeAmount(3L)
                .build();
        contentsVideoRepository.save(contentsVideo2);
        Contents contents4 = Contents.builder()
                .curriculum(curriculum2)
                .title("자료2")
                .type(ContentType.MATERIAL)
                .idx(2)
                .build();
        contentsRepository.save(contents4);
        ContentsMaterial contentsMaterial2 = ContentsMaterial.builder()
                .contents(contents4)
                .path("/material")
                .originName("test2")
                .savedName("2222")
                .postfix("pdf")
                .build();
        contentsMaterialRepository.save(contentsMaterial2);

        // 회사 - 코스 간 Contract 생성
        Contract contract1 = Contract.builder()
                .company(company1)
                .course(course1)
                .beginDate(LocalDate.of(2024, 9, 10))
                .endDate(LocalDate.of(2024, 12, 31))
                .state(ContractState.ACCEPTED)
                .attendeeAmount(10)
                .deposit(1000000L)
                .build();
        contractRepository.save(contract1);

        // 수강할 User 들 생성
        User student1 = User.builder()
                .username("st1")
                .name("학생1")
                .password("1111")
                .birth(LocalDate.of(1993, 3, 3))
                .phone("01010101010")
                .email("st1@ahim.com")
                .gender(Gender.MALE)
                .role(UserRole.EMPLOYEE)
                .build();
        userRepository.save(student1);
        User student2 = User.builder()
                .username("st2")
                .name("학생2")
                .password("2222")
                .birth(LocalDate.of(1993, 3, 3))
                .phone("01020202020")
                .email("st2@ahim.com")
                .gender(Gender.MALE)
                .role(UserRole.EMPLOYEE)
                .build();
        userRepository.save(student2);

        // 학생1, 2 회사1 의 부서 1과 2와 매핑 하는 Affiliation 생성
        Affiliation affiliation2 = Affiliation.builder()
                .department(department2)
                .user(student1)
                .isSupervisor(false)
                .approval(true)
                .build();
        affiliationRepository.save(affiliation2);
        Affiliation affiliation3 = Affiliation.builder()
                .department(department3)
                .user(student2)
                .isSupervisor(false)
                .approval(true)
                .build();
        affiliationRepository.save(affiliation3);

        // Contract 와 수강생들 매핑하는 Enrollment 생성
        Enrollment enrollment1 = Enrollment.builder()
                .user(student1)
                .contract(contract1)
                .state(EnrollmentState.ONPROGRESS)
                .certificateDate(null)
                .build();
        enrollmentRepository.save(enrollment1);
        Enrollment enrollment2 = Enrollment.builder()
                .user(student2)
                .contract(contract1)
                .state(EnrollmentState.ONPROGRESS)
                .certificateDate(null)
                .build();
        enrollmentRepository.save(enrollment2);

    }
}
