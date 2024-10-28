package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.course.common.ContentType;
import com.example.ahimmoyakbackend.course.dto.ContentsCreateRequestDto;
import com.example.ahimmoyakbackend.course.dto.ContentsInfoResponseDto;
import com.example.ahimmoyakbackend.course.dto.FileInfoDto;
import com.example.ahimmoyakbackend.course.entity.Contents;
import com.example.ahimmoyakbackend.course.entity.ContentsMaterial;
import com.example.ahimmoyakbackend.course.entity.ContentsVideo;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import com.example.ahimmoyakbackend.course.repository.ContentsMaterialRepository;
import com.example.ahimmoyakbackend.course.repository.ContentsRepository;
import com.example.ahimmoyakbackend.course.repository.ContentsVideoRepository;
import com.example.ahimmoyakbackend.course.repository.CurriculumRepository;
import com.example.ahimmoyakbackend.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {
    private final CurriculumRepository curriculumRepository;
    private final UserService userService;
    private final ContentsRepository contentsRepository;
    private final ContentsVideoRepository contentsVideoRepository;
    private final ContentsMaterialRepository contentsMaterialRepository;
    private final FileService fileService;

    private static final long MAX_FILE_SIZE = 1024L * 1024L * 1024L * 10L; // 10GB

    @Override
    @Transactional
    public boolean add(UserDetails userDetails, long curriculumId, ContentsCreateRequestDto requestDto) {
        Curriculum curriculum = curriculumRepository.findById(curriculumId).orElse(null);
        if (curriculum == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "존재하지 않는 커리큘럼입니다.");
        }
        if (!curriculum.getCourse().getTutor().equals(userService.getAuth(userDetails))) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "해당 코스의 튜터만 접근가능합니다.");
        }
        long count = contentsRepository.countByCurriculum(curriculum);
        FileInfoDto fileInfo = fileService.saveFile(requestDto.getFile(), requestDto.getType());
        Contents contents = contentsRepository.save(requestDto.toDto(curriculum, (int) count + 1));
        if(requestDto.getType().equals(ContentType.VIDEO)) {
            contentsVideoRepository.save(ContentsVideo.builder()
                    .contents(contents)
                    .path(fileInfo.path())
                    .originName(fileInfo.originName())
                    .savedName(fileInfo.savedName())
                    .postfix(fileInfo.postfix())
                    .timeAmount((long) fileInfo.duration().doubleValue())
                    .build()
            );
        }else {
            contentsMaterialRepository.save(ContentsMaterial.builder()
                    .contents(contents)
                    .path(fileInfo.path())
                    .originName(fileInfo.originName())
                    .savedName(fileInfo.savedName())
                    .postfix(fileInfo.postfix())
                    .build()
            );
        }
        return true;
    }

    @Override
    public ContentsInfoResponseDto getInfo(long contentsId) {
        Contents contents = contentsRepository.findById(contentsId).orElse(null);
        if (contents == null) {
            throw new ApiException(HttpStatus.NOT_FOUND, "콘텐츠를 찾을 수 없습니다.");
        }
        String fileInfo;
        if(contents.getType().equals(ContentType.VIDEO)) {
            ContentsVideo contentsVideo = contentsVideoRepository.findByContents(contents);
            fileInfo = contentsVideo.getSavedName() + contentsVideo.getPostfix();
        }else {
            ContentsMaterial contentsMaterial = contentsMaterialRepository.findByContents(contents);
            fileInfo = contentsMaterial.getSavedName() + contentsMaterial.getPostfix();
        }
        return ContentsInfoResponseDto.from(contents, fileInfo);
    }
}
