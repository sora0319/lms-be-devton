package com.example.ahimmoyakbackend.auth.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.dto.*;
import com.example.ahimmoyakbackend.auth.entity.RefreshToken;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.jwt.JwtUtil;
import com.example.ahimmoyakbackend.auth.repository.RefreshTokenRepository;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserJoinResponseDTO create(UserJoinRequestDTO requestDto) {

        User user = User.builder()
                .username(requestDto.getUsername())
                .name(requestDto.getName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .birth(requestDto.getBirth())
                .email(requestDto.getEmail())
                .gender(requestDto.getGender())
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

        JwsDTO jwsDto = jwtUtil.createAllToken(findUser.getUsername(), findUser.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsername(requestDTO.getUsername());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(jwsDto.getRefreshToken()));
        } else {
            RefreshToken newToken = RefreshToken.builder()
                    .refreshToken(jwsDto.getRefreshToken())
                    .username(requestDTO.getUsername())
                    .build();

            refreshTokenRepository.save(newToken);
        }

        response.addHeader(JwtUtil.ACCESS_TOKEN, jwsDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, jwsDto.getRefreshToken());

        return UserLoginResponseDTO.builder()
                .msg("로그인 완료")
                .build();
    }

    public User getAuth(UserDetailsImpl userDetails) {
        return userRepository.findUserByUsername(userDetails.getUsername()).orElseThrow(
                ()-> new IllegalArgumentException("인증되지 않은 사용자입니다.")
        );

    }
}