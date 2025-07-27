package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.diary.model.DiaryGetRes;
import com.otd.onetoday_back.diary.model.DiaryPostAndUploadRes;
import com.otd.onetoday_back.diary.model.DiaryPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 일기 저장 + 이미지 업로드
     */
    public DiaryPostAndUploadRes saveDiaryAndHandleUpload(int memberId, DiaryPostReq req) {
        req.setMemberNoLogin(memberId);

        String savedImageName = null;
        List<MultipartFile> diaryImageFiles = req.getDiaryImageFiles();

        if (diaryImageFiles != null && !diaryImageFiles.isEmpty()) {
            MultipartFile image = diaryImageFiles.get(0);
            savedImageName = storeImage(image);
        }

        req.setDiaryImage(savedImageName);
        diaryMapper.save(req);

        return new DiaryPostAndUploadRes(req.getId(), savedImageName);
    }

    /**
     * 단일 일기 조회
     */
    public DiaryGetRes findById(int id, int memberId) {
        DiaryGetRes res = diaryMapper.findById(id, memberId);
        if (res == null) {
            throw new CustomException("해당 일기를 찾을 수 없거나 권한이 없습니다.", 404);
        }
        return res;
    }

    /**
     * 일기 수정
     */
    public void update(DiaryPostReq req, int memberId) {
        DiaryGetRes original = diaryMapper.findById(req.getId(), memberId);
        if (original == null) {
            throw new CustomException("존재하지 않거나 수정 권한이 없습니다.", 403);
        }

        List<MultipartFile> diaryImageFiles = req.getDiaryImageFiles();
        if (diaryImageFiles != null && !diaryImageFiles.isEmpty()) {
            MultipartFile newImage = diaryImageFiles.get(0);
            String newFileName = storeImage(newImage);
            deleteImageIfExists(original.getDiaryImage());
            req.setDiaryImage(newFileName);
        } else {
            req.setDiaryImage(original.getDiaryImage());
        }

        req.setMemberNoLogin(memberId);
        diaryMapper.update(req);
    }

    /**
     * 일기 삭제
     */
    public void delete(int id, int memberId) {
        DiaryGetRes diary = diaryMapper.findById(id, memberId);
        if (diary == null) {
            throw new CustomException("삭제할 수 없거나 권한이 없습니다.", 403);
        }

        diaryMapper.delete(id, memberId);
        deleteImageIfExists(diary.getDiaryImage());
    }

    /**
     * 일기 목록 조회 (페이지네이션)
     */
    public List<DiaryGetRes> findAll(int memberId, int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        return diaryMapper.findAll(memberId, pageSize, offset);
    }

    public int countAll(int memberId) {
        return diaryMapper.countAll(memberId);
    }

    /**
     * 이미지 저장 처리
     */
    private String storeImage(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path savePath = Paths.get(uploadDir).resolve(fileName);

        try {
            Files.createDirectories(savePath.getParent());
            file.transferTo(savePath.toFile());
            return fileName;
        } catch (IOException e) {
            log.error("이미지 저장 실패: {}", e.getMessage(), e);
            throw new CustomException("이미지 업로드에 실패했습니다.", 500);
        }
    }

    /**
     * 이미지 삭제 처리
     */
    private void deleteImageIfExists(String fileName) {
        if (fileName == null || fileName.isBlank()) return;

        Path filePath = Paths.get(uploadDir).resolve(fileName);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.warn("이미지 삭제 실패: {}", fileName);
        }
    }
}