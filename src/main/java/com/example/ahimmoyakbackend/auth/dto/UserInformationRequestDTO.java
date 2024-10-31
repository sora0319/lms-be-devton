package com.example.ahimmoyakbackend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationRequestDTO {
    private String password;
    private String phone;
    private String email;
}
