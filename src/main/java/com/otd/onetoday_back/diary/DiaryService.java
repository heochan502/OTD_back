package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.diary.model.*;
import jakarta.annotation.PostConstruct;
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

    @Value("${upload.base-path:/home/download/diary}")
    private String basePath;

    @PostConstruct
    public void adjustUploadPathForWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");

        if (os.contains("win") && uploadDir != null &&  uploadDir.startsWith("/home/download/")) {
            String subFolder = uploadDir.substring("/home/download/".length());
            Path baseDownload = Paths.get(userHome, "Downloads", subFolder).toAbsolutePath().normalize();
            uploadDir = baseDownload.toString();
            log.warn("Windows 환경 감지. uploadDir을 {}로 변경합니다.", uploadDir);
        }
        Path path = Paths.get(uploadDir).toAbsolutePath().normalize();
        uploadDir = path.toString();

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            log.error("저장 경로 생성 실패: {}", e.getMessage(), e);
            throw new CustomException("저장 경로 생성 중 오류 발생", 500);
        }
        log.info("실제 사용될 uploadDir 경로: {}", uploadDir);
    }

    public DiaryListRes findAll(DiaryGetReq req) {
        int offset = (req.getCurrentPage() - 1) * req.getPageSize();
        req.setOffset(offset);
        List<DiaryGetRes> diaryList = diaryMapper.findAll(req);
        int totalCount = diaryMapper.getTotalCount(req);
        return new DiaryListRes(diaryList, totalCount);
    }

    public DiaryGetRes findById(int diaryId, int memberId) {
        Map<String, Object> param = new HashMap<>();
        param.put("diaryId", diaryId);
        param.put("memberNoLogin", memberId);
        DiaryGetRes diary = diaryMapper.findById(param);
        if (diary == null) {
            throw new CustomException("존재하지 않거나 접근 권한이 없습니다.", 404);
        }
        return diary;
    }

    public DiaryPostAndUploadRes save(DiaryPostReq req, MultipartFile diaryImage) {
        if (req.getMemberNoLogin() <= 0) {
            throw new CustomException("로그인이 필요합니다.", 403);
        }
        if(diaryImage != null && !diaryImage.isEmpty()) {
            String fileName = saveImage(diaryImage);
            req.setDiaryImage(fileName);
        }
        diaryMapper.save(req);

        return new DiaryPostAndUploadRes(
                req.getMemberNoLogin(),
                req.getDiaryId(),
                req.getDiaryName(),
                req.getDiaryContent(),
                req.getDiaryImage()
        );
    }

    public DiaryPostAndUploadRes update(DiaryPutReq req, MultipartFile diaryImage) {
        if (req.getMemberNoLogin() <= 0) {
            throw new CustomException("로그인이 필요합니다.", 401);
        }

        Map<String, Object> param = new HashMap<>();
        param.put("diaryId", req.getDiaryId());
        param.put("memberNoLogin", req.getMemberNoLogin());

        DiaryGetRes existing = diaryMapper.findById(param);
        if (existing == null) {
            throw new CustomException("존재하지 않거나 수정 권한이 없습니다.", 403);
        }

        if (diaryImage != null && !diaryImage.isEmpty()) {
            String fileName = saveImage(diaryImage);
            req.setDiaryImage(fileName);

            if (existing.getDiaryImage() != null) {
                deleteFileIfExists(existing.getDiaryImage());
            }
        } else {
            req.setDiaryImage(existing.getDiaryImage());
        }

        diaryMapper.update(req);

        return new DiaryPostAndUploadRes(
                req.getMemberNoLogin(),
                req.getDiaryId(),
                req.getDiaryName(),
                req.getDiaryContent(),
                req.getDiaryImage()
        );
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

        if (existing.getDiaryImage() != null) {
            deleteFileIfExists(existing.getDiaryImage());
        }

        diaryMapper.delete(params);
    }

    private String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new CustomException("빈 파일은 저장할 수 없습니다.", 400);
        }
        if (uploadDir == null || uploadDir.trim().isEmpty()) {
            throw new CustomException("업로드 경로가 설정되지 않았습니다.", 500);
        }

        String originalFilename = file.getOriginalFilename();
        String ext = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".bin";
        String safeFileName = UUID.randomUUID().toString() + ext;

        Path baseDir = Paths.get(uploadDir.trim(), "diary").normalize();
        Path target = baseDir.resolve(safeFileName).normalize();

        if (!target.startsWith(baseDir)) {
            throw new CustomException("잘못된 파일 경로입니다.", 400);
        }

        try {
            Files.createDirectories(baseDir);
            file.transferTo(target.toFile());
            log.info("✅ 이미지 저장 완료: {}", safeFileName);
            return safeFileName;
        } catch (IOException e) {
            log.error("❌ 이미지 저장 실패", e);
            throw new CustomException("파일 저장 중 오류가 발생했습니다.: " + e.getMessage(), 500);
        }
    }

    private void deleteFileIfExists(String fileName) {
        if (fileName == null || fileName.isEmpty()) return;
        try {
            Path filePath = Paths.get(uploadDir, "diary", fileName);
            Files.deleteIfExists(filePath);
            log.info("🗑️ 이미지 삭제 완료: {}", filePath.toAbsolutePath());
        } catch (IOException e) {
            log.warn("⚠️ 이미지 삭제 실패: {}", fileName);
        }
    }

    private String getExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) return "jpg";
        return originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
    }
}