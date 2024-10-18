package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.auth.common.Gender;
import com.example.ahimmoyakbackend.auth.common.UserRole;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.company.entity.Department;
import com.example.ahimmoyakbackend.company.repository.AffiliationRepository;
import com.example.ahimmoyakbackend.company.repository.CompanyRepository;
import com.example.ahimmoyakbackend.company.repository.DepartmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.example.ahimmoyakbackend.auth.common.UserRole.NORMAL;

@SpringBootTest
@Transactional
public class CompanyServiceTest {

    @InjectMocks
    CompanyService companyService;

    @MockBean
    CompanyRepository companyRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AffiliationRepository affiliationRepository;

    @BeforeEach
    void beforeEach() {
        User user1 = new User(1L, "nick","name","password123#", LocalDate.now(), "01025351123", "email@samsun.co.kr", Gender.MALE,NORMAL);
        Company company1 = new Company(1L, "삼성", "이재용", "1234567891324", "jeje@samsung.co.kr", "samsung.co.kr", "123456678");
        Department department1 = new Department(1L, "영업부", company1);
        Affiliation affiliation1 = new Affiliation(1L,false,false,department1,user1);
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
        companyRepository.deleteAll();
        departmentRepository.deleteAll();
        affiliationRepository.deleteAll();

    }


    @Test
    @DisplayName("affiliaionId, companyId 일치할때 부서 등록 확인")
    public void successDepartmentInsert() throws Exception {
        // Given
        Long companyId = 1L;
        Long affiliationId = 1L;
        Department department = Department.builder().id(1L).build();


        // When



        // Then
    }

    @Test
    @DisplayName("부서 등록 실패")
    public void failDepartmentInsert() throws Exception {}

    @Test
    @DisplayName("부서 수정 성공")
    public void successDepartmentUpdate() throws Exception {}
}
