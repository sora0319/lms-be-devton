package com.example.ahimmoyakbackend.auth.dto;

import com.example.ahimmoyakbackend.auth.dto.validation.ValidEnum;
import com.example.ahimmoyakbackend.auth.entity.common.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserJoinRequestDTO {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(message = "잘못된 비밀번호 형식입니다."
            , regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @PastOrPresent
    private LocalDate birth;

    @Email(message = "유효하지 않은 이메일 형식입니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @ValidEnum(enumClass = Gender.class)
    private Gender gender;
}