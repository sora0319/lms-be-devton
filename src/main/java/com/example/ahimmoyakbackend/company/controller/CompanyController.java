package com.example.ahimmoyakbackend.company.controller;

import com.example.ahimmoyakbackend.company.dto.CompanyInquiryUserRequestDto;
import com.example.ahimmoyakbackend.company.dto.CompanyInquiryUserResponseDto;
import com.example.ahimmoyakbackend.company.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CompanyController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @RequestMapping("/supervisor")
    public ResponseEntity<List<CompanyInquiryUserResponseDto>> getUserList(@RequestParam("companyId") Long companyId,
                                                                    @RequestParam("affiliationId") Long affiliationId,
                                                                    @RequestParam("departmentId") Long departmentId
    ) {
        List<CompanyInquiryUserResponseDto> userList = companyService.getUserByCompany(companyId, affiliationId, departmentId);
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }
}
