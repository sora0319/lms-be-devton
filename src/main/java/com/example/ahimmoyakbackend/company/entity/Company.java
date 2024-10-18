package com.example.ahimmoyakbackend.company.entity;

import com.example.ahimmoyakbackend.company.dto.CompanyUpdateRequestDto;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String ownerName;

    @Column(nullable = false)
    private String businessNumber;

    @Column(length = 100)
    private String email;

    @Column(length = 50, nullable = false)
    private String emailDomain;

    @Column(nullable = false)
    private String phone;

    public void patch(CompanyUpdateRequestDto requestDto) {
        if (this.name.equals(requestDto.getName()))
            this.name = requestDto.getName();
        if (this.ownerName != requestDto.getOwnerName())
            this.ownerName = requestDto.getOwnerName();
        if (this.businessNumber != requestDto.getBusinessNumber())
            this.businessNumber = requestDto.getBusinessNumber();
        if (this.email != requestDto.getEmail())
            this.email = requestDto.getEmail();
        if (this.phone != requestDto.getPhone())
            this.phone = requestDto.getPhone();
    }
}
