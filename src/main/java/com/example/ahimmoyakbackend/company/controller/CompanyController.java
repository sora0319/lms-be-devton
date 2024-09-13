package com.example.ahimmoyakbackend.company.controller;

import com.example.ahimmoyakbackend.company.dto.CompanyDeleteDepartmentResponseDto;
import com.example.ahimmoyakbackend.company.dto.CompanyEnrollDepartmentRequestDto;
import com.example.ahimmoyakbackend.company.dto.CompanyEnrollDepartmentResponseDto;
import com.example.ahimmoyakbackend.company.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CompanyController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

//    @RequestMapping("/v1/supervisor", method = RequestMethod.POST)
//    public ResponseEntity<List<CompanyInquiryUserResponseDto>> getUserList(@RequestParam("companyId") Long companyId,
//                                                                           @RequestParam("affiliationId") Long affiliationId,
//                                                                           @RequestParam("departmentId") Long departmentId
//    ) {
//        List<CompanyInquiryUserResponseDto> userList = companyService.getUserByCompany(companyId, affiliationId, departmentId);
//        return ResponseEntity.status(HttpStatus.OK).body(userList);
//    }

    @RequestMapping(value = "/v1/supervisor", method = RequestMethod.POST)
    public ResponseEntity<CompanyEnrollDepartmentResponseDto> enrollDepartment(@RequestParam("companyId") Long companyId,
                                                                               @RequestParam("affiliationId") Long affiliationId,
                                                                               @RequestBody CompanyEnrollDepartmentRequestDto requestDto
    ) {
        CompanyEnrollDepartmentResponseDto enrolled = companyService.enroll(companyId, affiliationId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(enrolled);
    }

    @RequestMapping(value = "/v1/supervisor", method = RequestMethod.DELETE)
    public ResponseEntity<CompanyDeleteDepartmentResponseDto> deleteDepartment(@RequestParam("companyId") Long companyId,
                                                                               @RequestParam("affiliationId") Long affiliationId,
                                                                               @RequestParam("departmentId") Long departmentId

    ) {
        CompanyDeleteDepartmentResponseDto deleted = companyService.delete(companyId, affiliationId, departmentId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}


