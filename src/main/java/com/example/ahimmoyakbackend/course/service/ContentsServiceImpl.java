package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.course.common.ContentType;
import com.example.ahimmoyakbackend.course.dto.ContentsCreateRequestDto;
import com.example.ahimmoyakbackend.course.dto.FileInfoDto;
import com.example.ahimmoyakbackend.course.entity.Contents;
import com.example.ahimmoyakbackend.course.entity.ContentsVideo;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import com.example.ahimmoyakbackend.course.repository.ContentsMaterialRepository;
import com.example.ahimmoyakbackend.course.repository.ContentsRepository;
import com.example.ahimmoyakbackend.course.repository.ContentsVideoRepository;
import com.example.ahimmoyakbackend.course.repository.CurriculumRepository;
import com.example.ahimmoyakbackend.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {
    private final CurriculumRepository curriculumRepository;
    private final UserService userService;
    private final ContentsRepository contentsRepository;
    private final ContentsVideoRepository contentsVideoRepository;
    private final ContentsMaterialRepository contentsMaterialRepository;

    private static final long MAX_FILE_SIZE = 1024L * 1024L * 1024L * 10L; // 10GB

    @Override
    @Transactional
    public boolean addVideo(UserDetails userDetails, long curriculumId, ContentsCreateRequestDto requestDto) {
        Curriculum curriculum = curriculumRepository.findById(curriculumId).orElse(null);
        if(curriculum == null || curriculum.getCourse().getTutor().equals(userService.getAuth(userDetails))) {
            return false;
        }
        long count = contentsRepository.countByCurriculum(curriculum);
        FileInfoDto fileInfo = saveFile(requestDto.getFile(), requestDto.getType());
        Contents contents = contentsRepository.save(requestDto.toDto(curriculum, (int) count++));
        contentsVideoRepository.save(ContentsVideo.builder()
                .path(fileInfo.path())
                .originName(fileInfo.originName())
                .savedName(fileInfo.savedName())
                .postfix(fileInfo.postfix())
                .timeAmount((long) fileInfo.duration().doubleValue())
                .build()
        );
        return false;
    }

    private FileInfoDto saveFile(MultipartFile file, ContentType contentType) {
        if(file.getSize() > MAX_FILE_SIZE) throw new ApiException(HttpStatus.PAYLOAD_TOO_LARGE, "파일용량이 너무 큼");
        String postfix = getPostfix(file.getOriginalFilename());
        String savedName = UUID.randomUUID() + postfix;
        Path filePath;
        if(contentType.equals(ContentType.VIDEO)){
             filePath = Paths.get(getPath("/video"), savedName);
        }else {
            filePath = Paths.get(getPath("/material"), savedName);
        }
        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        if(contentType.equals(ContentType.VIDEO)){
            double duration = getVideoDuration(filePath.toFile());
            return FileInfoDto.builder()
                    .path(filePath.toString())
                    .originName(subPostfix(file.getOriginalFilename()))
                    .savedName(savedName)
                    .postfix(postfix)
                    .duration(duration)
                    .build();
        }
        return FileInfoDto.builder()
                .path(filePath.toString())
                .originName(subPostfix(file.getOriginalFilename()))
                .savedName(savedName)
                .postfix(postfix)
                .build();
    }

    private String getPostfix(String fileName) {
        if(fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private String subPostfix(String fileName) {
        if(fileName == null || !fileName.contains(".")) return fileName;
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    private String getPath(String optionalPath) {
        String directory = "storage" + optionalPath;
        Path path = Paths.get(directory);
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "저장공간을 찾을 수 없음");
            }
        }
        return path.toString();
    }

    private double getVideoDuration(File videoFile) {
        try {
            // FrameGrab을 사용해 동영상 메타데이터에서 총 프레임 수와 FPS를 얻음
            FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(videoFile));
            grab.seekToSecondPrecise(0);

            int totalFrames = 0;
            Picture picture;

            while ((picture = grab.getNativeFrame()) != null) {
                totalFrames++;
            }

            // FPS 가져오기
            double fps = grab.getVideoTrack().getMeta().getTotalDuration() / totalFrames;

            return totalFrames / fps;
        } catch (IOException | JCodecException e) {
            return -1;
        }
    }
}
