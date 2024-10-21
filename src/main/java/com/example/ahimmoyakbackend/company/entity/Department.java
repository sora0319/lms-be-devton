package com.example.ahimmoyakbackend.company.entity;

import com.example.ahimmoyakbackend.company.dto.CompanyUpdateDepartmentRequestDto;
import com.example.ahimmoyakbackend.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "department")
public class Department extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;


    public void patch(CompanyUpdateDepartmentRequestDto requestDto) {
        if (!Objects.equals(this.name, requestDto.getDepartmentName())) {
            this.name = requestDto.getDepartmentName();
        }
    }
}