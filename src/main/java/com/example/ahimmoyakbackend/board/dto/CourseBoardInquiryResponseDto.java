package com.example.ahimmoyakbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseBoardInquiryResponseDto {

    private String courseTitle;

    private List<CourseBoardsResponseDto> boards;

    private Pagination pagination;

}
