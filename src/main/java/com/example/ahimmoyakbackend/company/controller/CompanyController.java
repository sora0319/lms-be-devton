package com.example.ahimmoyakbackend.company.controller;

import com.example.ahimmoyakbackend.auth.jwt.JwtUtil;
import com.example.ahimmoyakbackend.company.dto.*;
import com.example.ahimmoyakbackend.company.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtUtil jwtUtil;

    // User
    @RequestMapping(value = "/v1/supervisor/", method = RequestMethod.GET)
    public ResponseEntity<List<CompanyInquiryUserResponseDto>> getUserList(@RequestParam("companyId") Long companyId,
                                                                           @RequestParam("departmentId") Long departmentId
    ) {
        List<CompanyInquiryUserResponseDto> userList = companyService.getUserbyCompany(companyId, departmentId);
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    // Department
    @RequestMapping(value = "/v1/supervisor/department", method = RequestMethod.POST)
    public ResponseEntity<CompanyEnrollDepartmentResponseDto> enrollDepartment(@RequestParam("companyId") Long companyId,
                                                                               @RequestParam("affiliationId") Long affiliationId,
                                                                               @RequestBody CompanyEnrollDepartmentRequestDto requestDto
    ) {
        CompanyEnrollDepartmentResponseDto enrolled = companyService.enrollDepartment(companyId, affiliationId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(enrolled);
    }

    @RequestMapping(value = "/v1/supervisor/department", method = RequestMethod.DELETE)
    public ResponseEntity<CompanyDeleteDepartmentResponseDto> deleteDepartment(@RequestParam("companyId") Long companyId,
                                                                               @RequestParam("affiliationId") Long affiliationId,
                                                                               @RequestParam("departmentId") Long departmentId

    ) {
        CompanyDeleteDepartmentResponseDto deleted = companyService.deleteDepartment(companyId, affiliationId, departmentId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

    @RequestMapping(value = "v1/supervisor/department", method = RequestMethod.PATCH)
    public ResponseEntity<CompanyUpdateDepartmentResponseDto> updateDepartment(@RequestParam("companyId") Long companyId,
                                                                               @RequestParam("departmentId") Long departmentId,
                                                                               @RequestBody CompanyUpdateDepartmentRequestDto requestDto

    ) {
        CompanyUpdateDepartmentResponseDto updated = companyService.updateDepartment(companyId, departmentId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @RequestMapping(value = "v1/supervisor/department", method = RequestMethod.GET)
    public ResponseEntity<List<CompanyInquiryDepartmentResponseDto>> inquiryDepartment(@RequestParam("companyId") Long companyId
    ) {
        List<CompanyInquiryDepartmentResponseDto> departmentList = companyService.getDepartmentCompanyId(companyId);
        return ResponseEntity.status(HttpStatus.OK).body(departmentList);
    }

    // Company
    @RequestMapping(value = "v1/supervisor/company", method = RequestMethod.POST)
    public ResponseEntity<CompanyEnrollResponseDto> enrollCompany(@RequestParam("userId") Long userId,
                                                                  @RequestBody CompanyEnrollRequestDto requestDto) {
        CompanyEnrollResponseDto enrolled = companyService.enrollCompany(userId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(enrolled);
    }

    @RequestMapping(value = "v1/supervisor/company", method = RequestMethod.PATCH)
    public ResponseEntity<CompanyUpdateResponseDto> updateCompany(@RequestParam("companyId") Long companyId,
                                                                  @RequestBody CompanyUpdateRequestDto requestDto

    ) {
        CompanyUpdateResponseDto updated = companyService.updateCompany(companyId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @PostMapping("/v1/company/search")
    public ResponseEntity<List<FindCompanyResponseDto>> findcompanyName(@RequestBody FindCompanyRequestDto requestDto) {
        List<FindCompanyResponseDto> companyName = companyService.findCompanyName(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(companyName);
    }

    @GetMapping("/v1/users/companyId")
    public ResponseEntity<Long> getLoggedInUserCompanyId(HttpServletRequest request) {

        String token = jwtUtil.getTokenFromHeader(request, JwtUtil.ACCESS_TOKEN);

        if(token != null && jwtUtil.validateToken(token)) {
            Long companyId = companyService.getCompanyIdFromToken(token);

            if (companyId != null) {
                return ResponseEntity.status(HttpStatus.OK).body(companyId);
            } else { // 소속된 회사 없음
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }
        //유효하지 않은 토큰
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

}


