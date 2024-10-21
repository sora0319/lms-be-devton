package com.example.ahimmoyakbackend.company.entity;

import com.example.ahimmoyakbackend.company.dto.CompanyUpdateRequestDto;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import java.util.Objects;

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

    @OneToMany(mappedBy = "id")
    private List<CourseProvide> courseProvide;

    public void patch(CompanyUpdateRequestDto requestDto) {
        if (!Objects.equals(this.name,requestDto.getName()))
            this.name = requestDto.getName();
        if (!Objects.equals(this.ownerName, requestDto.getOwnerName()))
            this.ownerName = requestDto.getOwnerName();
        if (!Objects.equals(this.email, requestDto.getEmail()))
            this.email = requestDto.getEmail();
        if (!Objects.equals(this.phone, requestDto.getPhone()))
            this.phone = requestDto.getPhone();
    }
}
