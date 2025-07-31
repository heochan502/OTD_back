package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.diary.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;

    @Value("${constants.file.directory}")
    private String uploadDir;

    public DiaryListRes findAll(DiaryGetReq req) {
        int offset = (req.getCurrentPage() - 1) * req.getPageSize();
        req.setOffset(offset);
        List<DiaryGetRes> diaryList = diaryMapper.findAll(req);
        int totalCount = diaryMapper.getTotalCount(req);
        return new DiaryListRes(diaryList, totalCount);
    }

    public DiaryGetRes findById(int diaryId, int memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("diaryId", diaryId);
        params.put("memberNoLogin", memberId);

        DiaryGetRes diary = diaryMapper.findById(params);
        if (diary == null) {
            throw new CustomException("존재하지 않거나 권한이 없는 다이어리입니다.", 401);
        }
        return diary;
    }

    public DiaryPostAndUploadRes save(DiaryPostReq req, MultipartFile diaryImage) {
        if (req.getMemberNoLogin() <= 0) {
            throw new CustomException("로그인이 필요합니다.", 403);
        }

        if (diaryImage != null && !diaryImage.isEmpty()) {
            String fileName = saveImage(diaryImage);
            req.setDiaryImage(fileName);
        }

        diaryMapper.insert(req);
        return new DiaryPostAndUploadRes(req.getDiaryId(), req.getDiaryImage());
    }

    public DiaryPostAndUploadRes update(DiaryPutReq req, MultipartFile diaryImage) {
        if (req.getMemberNoLogin() <= 0) {
            throw new CustomException("로그인이 필요합니다.", 401);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("diaryId", req.getDiaryId());
        params.put("memberNoLogin", req.getMemberNoLogin());

        DiaryGetRes existing = diaryMapper.findById(params);
        if (existing == null) {
            throw new CustomException("존재하지 않거나 수정 권한이 없습니다.", 403);
        }

        if (diaryImage != null && !diaryImage.isEmpty()) {
            deleteImageFile(existing.getDiaryImage());
            String fileName = saveImage(diaryImage);
            req.setDiaryImage(fileName);
        } else {
            req.setDiaryImage(existing.getDiaryImage());
        }

        diaryMapper.update(req);
        return new DiaryPostAndUploadRes(req.getDiaryId(), req.getDiaryImage());
    }

    public void delete(int diaryId, int memberId) {
        if (memberId <= 0) {
            throw new CustomException("로그인이 필요합니다.", 401);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("diaryId", diaryId);
        params.put("memberNoLogin", memberId);

        DiaryGetRes existing = diaryMapper.findById(params);
        if (existing == null) {
            throw new CustomException("존재하지 않거나 삭제 권한이 없습니다.", 403);
        }

        deleteImageFile(existing.getDiaryImage());
        diaryMapper.delete(params);
    }

    private String saveImage(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String ext = "." + getExtension(originalFilename);
            String newFileName = UUID.randomUUID() + ext;

            Path directoryPath = Paths.get(uploadDir);
            Files.createDirectories(directoryPath);
            Path filePath = directoryPath.resolve(newFileName);

            log.info("📁 저장할 경로: {}", filePath.toAbsolutePath());
            log.info("📎 업로드 파일명: {}", originalFilename);

            Files.write(filePath, file.getBytes());

            log.info("✅ 이미지 저장 완료: {}", newFileName);

            return newFileName;
        } catch (IOException e) {
            log.error("❌ 이미지 저장 실패", e);
            throw new CustomException("이미지 업로드 실패: " + e.getMessage(), 500);
        }
    }

    private void deleteImageFile(String filename) {
        if (filename == null || filename.isEmpty()) return;
        try {
            Path filePath = Paths.get(uploadDir, filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.warn("이미지 삭제 실패: {}", filename);
        }
    }

    private String getExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) return "jpg";
        return originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
    }
}