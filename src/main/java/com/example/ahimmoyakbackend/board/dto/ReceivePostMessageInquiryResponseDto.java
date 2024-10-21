package com.example.ahimmoyakbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceivePostMessageInquiryResponseDto {

    private List<ReceivePostMessageResponseDto> messages;

    private Pagination pages;

}
