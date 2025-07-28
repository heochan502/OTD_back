package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.diary.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * 일기 등록 + 이미지 업로드
     */
    @Transactional
    public DiaryPostAndUploadRes saveDiaryAndHandleUpload(int userId, DiaryPostReq req) {
        req.setMemberNoLogin(userId);

        String savedFileName = handleSingleFileUpload(req.getDiaryImageFiles());
        req.setDiaryImage(savedFileName);

        diaryMapper.save(req);
        return new DiaryPostAndUploadRes(req.getId(), savedFileName);
    }

    /**
     * 일기 목록 + 페이징
     */
    public DiaryListRes findAll(DiaryGetReq req) {
        int page = Math.max(req.getCurrentPage(), 1);
        int size = Math.max(req.getPageSize(), 5);
        int offset = (page - 1) * size;

        req.setCurrentPage(page);
        req.setPageSize(size);
        req.setOffset(offset);

        List<DiaryGetRes> list = diaryMapper.findAll(req);
        int count = diaryMapper.getTotalCount(req);

        return new DiaryListRes(list, count);
    }

    /**
     * 일기 단건 조회 (로그인 사용자 소유 확인 포함)
     */
    public DiaryGetRes findOwnedDiaryById(int id, int userId) {
        DiaryGetRes diary = diaryMapper.findById(id);
        if (diary == null) {
            throw new CustomException("일기 없음", 404);
        }
        if (diary.getMemberNoLogin() != userId) {
            throw new CustomException("권한 없음", 403);
        }
        return diary;
    }

    /**
     * 일기 수정 + 이미지 변경
     */
    @Transactional
    public int modify(DiaryPutReq req) {
        DiaryGetRes existing = findOwnedDiaryById(req.getId(), req.getMemberNoLogin());

        // 기존 이미지 삭제
        deleteFileIfExists(existing.getDiaryImage());

        // 새 이미지 업로드
        String newSaved = handleSingleFileUpload(req.getDiaryImageFiles());
        req.setDiaryImage(newSaved);

        return diaryMapper.modify(req);
    }

    /**
     * 일기 삭제 + 이미지 삭제
     */
    @Transactional
    public int deleteById(int id, int userId) {
        DiaryGetRes existing = findOwnedDiaryById(id, userId);
        deleteFileIfExists(existing.getDiaryImage());
        return diaryMapper.deleteById(id);
    }

    /**
     * 단일 파일 업로드 처리
     */
    private String handleSingleFileUpload(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) return null;

        MultipartFile file = files.get(0);
        if (file == null || file.isEmpty()) return null;

        try {
            return saveFile(file);
        } catch (IOException e) {
            log.error("파일 업로드 실패", e);
            throw new CustomException("파일 업로드 실패", 500);
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(uploadDir));
        String uniqueName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path target = Paths.get(uploadDir).resolve(uniqueName);
        file.transferTo(target.toFile());
        return uniqueName;
    }

    private void deleteFileIfExists(String fileName) {
        if (fileName == null || fileName.isBlank()) return;
        try {
            Files.deleteIfExists(Paths.get(uploadDir).resolve(fileName));
        } catch (IOException e) {
            log.warn("파일 삭제 실패: {}", fileName);
        }
    }
}