package com.example.ahimmoyakbackend.company.dto;

import com.example.ahimmoyakbackend.global.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyGetAddressDto {
    private String base;
    private String detail;
    private Integer postal;

    public static CompanyGetAddressDto fromEntity(Address address) {
        return CompanyGetAddressDto.builder()
                .base(address.getBase())
                .detail(address.getDetail())
                .postal(address.getPostal())
                .build();
    }

}
