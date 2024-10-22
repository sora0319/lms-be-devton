package com.example.ahimmoyakbackend.company.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.company.dto.*;
import com.example.ahimmoyakbackend.company.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CompanyController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    private final UserService userService;

    // EmployeeList
    @GetMapping("/v1/supervisor/")
    public ResponseEntity<List<CompanyInquiryEmployeeListResponseDto>> getUserList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long companyId = userService.getAuth(userDetails).getId();

        List<CompanyInquiryEmployeeListResponseDto> userList = companyService.getUserbyCompany(companyId);
        return ResponseEntity.status(HttpStatus.OK).body(userList);

    }
    // EmployeeDetail

    // Department
    @PostMapping("/v1/supervisor/department")
    public ResponseEntity<CompanyEnrollDepartmentResponseDto> enrollDepartment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                               @RequestBody CompanyEnrollDepartmentRequestDto requestDto
    ) {
        Long companyId = userService.getAuth(userDetails).getId();
        Long affiliationId = userService.getAuth(userDetails).getAffiliation().getId();

        CompanyEnrollDepartmentResponseDto enrolled = companyService.enrollDepartment(companyId, affiliationId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(enrolled);
    }

    @DeleteMapping("/v1/supervisor/department")
    public ResponseEntity<CompanyDeleteDepartmentResponseDto> deleteDepartment(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long companyId = userService.getAuth(userDetails).getId();
        Long affiliationId = userService.getAuth(userDetails).getAffiliation().getId();
        Long departmentId = userService.getAuth(userDetails).getAffiliation().getDepartment().getId();

        CompanyDeleteDepartmentResponseDto deleted = companyService.deleteDepartment(companyId, affiliationId, departmentId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

    @PatchMapping("/v1/supervisor/department")
    public ResponseEntity<CompanyUpdateDepartmentResponseDto> updateDepartment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                               @RequestBody CompanyUpdateDepartmentRequestDto requestDto

    ) {
        Long companyId = userService.getAuth(userDetails).getId();
        Long departmentId = userService.getAuth(userDetails).getAffiliation().getDepartment().getId();

        CompanyUpdateDepartmentResponseDto updated = companyService.updateDepartment(companyId, departmentId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @GetMapping("/v1/supervisor/department")
    public ResponseEntity<List<CompanyInquiryDepartmentResponseDto>> inquiryDepartment(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long companyId = userService.getAuth(userDetails).getId();

        List<CompanyInquiryDepartmentResponseDto> departmentList = companyService.getDepartmentCompanyId(companyId);
        return ResponseEntity.status(HttpStatus.OK).body(departmentList);
    }


    // Company
    @PostMapping("/v1/supervisor/company")
    public ResponseEntity<CompanyEnrollResponseDto> enrollCompany(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                  @RequestBody CompanyEnrollRequestDto requestDto) {
        Long userId = userService.getAuth(userDetails).getId();
        System.out.println(userId);
        CompanyEnrollResponseDto enrolled = companyService.enrollCompany(userId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(enrolled);
    }

    @PatchMapping("/v1/supervisor/company")
    public ResponseEntity<CompanyUpdateResponseDto> updateCompany(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                  @RequestBody CompanyUpdateRequestDto requestDto

    ) {
        Long companyId = userService.getAuth(userDetails).getId();

        CompanyUpdateResponseDto updated = companyService.updateCompany(companyId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PostMapping("/v1/company/search")
    public ResponseEntity<List<FindCompanyResponseDto>> findcompanyName(@RequestBody FindCompanyRequestDto requestDto) {
        List<FindCompanyResponseDto> companyName = companyService.findCompanyName(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(companyName);
    }

    @GetMapping("/v1/users/companyId")
    public ResponseEntity<Long> getLoggedInUserCompanyId(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long employeeCompanyId = companyService.getCompanyIdFromToken(userService.getAuth(userDetails));

        if (employeeCompanyId != null) {
            return ResponseEntity.status(HttpStatus.OK).body(employeeCompanyId);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}


