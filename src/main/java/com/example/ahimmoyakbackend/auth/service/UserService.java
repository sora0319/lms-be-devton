package com.example.ahimmoyakbackend.auth.service;

import com.example.ahimmoyakbackend.auth.common.UserRole;
import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.dto.*;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.exception.InvalidPasswordException;
import com.example.ahimmoyakbackend.auth.jwt.JwtTokenProvider;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.Department;
import com.example.ahimmoyakbackend.company.repository.AffiliationRepository;
import com.example.ahimmoyakbackend.company.repository.CompanyRepository;
import com.example.ahimmoyakbackend.company.repository.DepartmentRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    private final AffiliationRepository affiliationRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserJoinResponseDTO create(UserJoinRequestDTO requestDto) {

        User user = User.builder()
                .username(requestDto.getUsername())
                .name(requestDto.getName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .birth(requestDto.getBirth())
                .email(requestDto.getEmail())
                .gender(requestDto.getGender())
                .role(UserRole.NORMAL)
                .build();

        userRepository.save(user);

        return UserJoinResponseDTO.builder()
                .msg("회원가입 완료")
                .build();
    }

    public UserLoginResponseDTO login(UserLoginRequestDTO requestDTO, HttpServletResponse response) {
        User findUser = userRepository.findUserByUsername(requestDTO.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );

        if (!passwordEncoder.matches(requestDTO.getPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        JwsDTO jwsDto = jwtTokenProvider.createAllToken(findUser.getUsername(), findUser.getEmail(), findUser.getRole());

        response.addHeader(JwtTokenProvider.ACCESS_TOKEN, jwsDto.getAccessToken());
        response.addHeader(JwtTokenProvider.REFRESH_TOKEN, jwsDto.getRefreshToken());

        return UserLoginResponseDTO.builder()
                .msg("로그인 완료")
                .build();
    }

    public User getAuth(UserDetailsImpl userDetails) {
        return userRepository.findUserByUsername(userDetails.getUsername()).orElseThrow(
                ()-> new IllegalArgumentException("인증되지 않은 사용자입니다.")
        );

    }

    public UserReissueResponseDTO reissue() {

        return UserReissueResponseDTO.builder()
                .msg("refresh token reissue complete")
                .build();
    }

    public ExistNameResponseDTO checkExistName(ExistNameRequestDTO requestDTO) {
        try {
            if(!userRepository.existsByUsername(requestDTO.getUsername())) {
                throw new IllegalArgumentException("잘못된 요청입니다.");
            }
        } catch (Exception e) {
            return ExistNameResponseDTO.builder()
                    .message("true")
                    .build();
        }

        return ExistNameResponseDTO.builder()
                .message("false")
                .build();
    }

    public UserVerificationResponseDTO checkVerification(UserVerificationRequestDTO requestDTO, UserDetailsImpl userDetails) {
        User findUser = userRepository.findUserByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );

        if (!passwordEncoder.matches(requestDTO.getPassword(), findUser.getPassword())) {
            throw new InvalidPasswordException("false");
        }
        return UserVerificationResponseDTO
                .builder()
                .message("true")
                .build();
    }

    public UserInformationResponseDTO updatePersonalInformation(UserInformationRequestDTO requestDTO, UserDetailsImpl userDetails) {
        User findUser = userRepository.findUserByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );

        findUser.patch(requestDTO, passwordEncoder.encode(requestDTO.getPassword()));
        userRepository.save(findUser);

        return UserInformationResponseDTO.builder()
                .message("complete")
                .build();
    }

    public EmployeeJoinResponseDTO register(EmployeeJoinRequestDTO requestDTO, UserDetailsImpl userDetails) {
        User targetUser = userRepository.findUserByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("회원이 없습니다")
        );

        Department targetDepartment = departmentRepository.findById(Long.valueOf(requestDTO.getDepartmentId())).orElseThrow(
                () -> new IllegalArgumentException("부서가 없습니다")
        );

        if(!companyRepository.existsById(Long.valueOf(requestDTO.getCompanyId()))){
            throw new IllegalArgumentException("회사가 없습니다");
        }

        Affiliation affiliation = Affiliation.builder()
                .department(targetDepartment)
                .user(targetUser)
                .isSupervisor(false)
                .approval(true)
                .build();

        affiliationRepository.save(affiliation);

        targetUser.updateRole(UserRole.EMPLOYEE);
        userRepository.save(targetUser);

        return EmployeeJoinResponseDTO.builder()
                .message("register complete")
                .build();
    }

    public UserDeleteResponseDTO delete(UserDetailsImpl userDetails) {
        User targetUser = userRepository.findUserByUsername(userDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다"));
        targetUser.updateRole(UserRole.NONMEMBER);
        userRepository.save(targetUser);

        return UserDeleteResponseDTO.builder()
                .message("delete complete")
                .build();
    }
}