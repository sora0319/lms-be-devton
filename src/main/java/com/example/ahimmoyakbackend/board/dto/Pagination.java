package com.example.ahimmoyakbackend.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {

    private int currentPage;

    private int pageSize;

}