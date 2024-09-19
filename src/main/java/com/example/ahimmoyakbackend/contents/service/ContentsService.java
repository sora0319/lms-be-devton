package com.example.ahimmoyakbackend.contents.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.contents.dto.ContentsUploadRequestDTO;
import com.example.ahimmoyakbackend.course.entity.Contents;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import com.example.ahimmoyakbackend.course.repository.ContentsRepository;
import com.example.ahimmoyakbackend.course.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ContentsService {

    private final ContentsRepository contentsRepository;
    private final CurriculumRepository curriculumRepository;

    public ContentsUploadRequestDTO contentsUpload(Long curriculumId, ContentsUploadRequestDTO dto, User user, MultipartFile file) throws IOException {

        Curriculum curriculum = curriculumRepository.findById(curriculumId).orElseThrow(()-> new IllegalArgumentException("커리큘럼이 없습니다."));

        String filePath = "/upload/" + file.getOriginalFilename();
        File videoFile = new File(filePath);
        file.transferTo(videoFile); // 파일을 서버에 저장!


        Contents contents = Contents.builder()
                .title(dto.getTitle())
                .type(dto.getType())
                .curriculum(curriculum)
                .contentsPath(filePath) // 저장할걸 여기에!
                .build();

        contentsRepository.save(contents);

        return ContentsUploadRequestDTO.from(contents);


    }
}
