package com.example.ahimmoyakbackend.auth.jwt;

import com.example.ahimmoyakbackend.auth.dto.validation.SecurityExceptionDto;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.ahimmoyakbackend.auth.jwt.JwtUtil.ACCESS_TOKEN;
import static com.example.ahimmoyakbackend.auth.jwt.JwtUtil.REFRESH_TOKEN;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtUtil.getTokenFromHeader(request, ACCESS_TOKEN);
        String refreshToken = jwtUtil.getTokenFromHeader(request, REFRESH_TOKEN);

        if (accessToken != null) {
            if (jwtUtil.validateToken(accessToken)) {
                setAuthentication(jwtUtil.getUserInfoFromToken(accessToken));
                filterChain.doFilter(request, response);
            } else {
                jwtExceptionHandler(response, "Invalid Access Token.", HttpStatus.UNAUTHORIZED.value());
            }
            return;
        }

        if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {
            String username = jwtUtil.getUserInfoFromToken(refreshToken);
            User user = userRepository.findUserByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            String newAccessToken = jwtUtil.createToken(username, user.getEmail(), ACCESS_TOKEN);
            jwtUtil.setHeaderAccessToken(response, newAccessToken);
            setAuthentication(username);
            filterChain.doFilter(request, response);
            return;
        }

        if (refreshToken != null) {
            jwtExceptionHandler(response, "Invalid or Expired Refresh Token.", HttpStatus.UNAUTHORIZED.value());
            return;
        }

        filterChain.doFilter(request, response);

    }

    public void setAuthentication(String username) {
        Authentication authentication = jwtUtil.createAuthentication(username);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(SecurityExceptionDto.builder()
                    .statusCode(statusCode)
                    .msg(msg)
                    .build());
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}