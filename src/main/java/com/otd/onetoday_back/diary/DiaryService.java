//package com.otd.onetoday_back.diary;
//
//import com.otd.onetoday_back.common.model.CustomException;
//import com.otd.onetoday_back.diary.model.*;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.Collections;
//import java.util.List;
//import java.util.UUID;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class DiaryService {
//
//    private final DiaryMapper diaryMapper;
//
//    @Value("${constants.file.directory}")
//    private String uploadDir;
//
//    @PostConstruct
//    public void adjustUploadPathForWindows() {
//        String os = System.getProperty("os.name").toLowerCase();
//        if (os.contains("win") && uploadDir.startsWith("/home")) {
//            uploadDir = "C:/2025_swstudy/upload";
//            log.warn("Windows 환경 감지됨. uploadDir을 {} 로 강제 설정합니다.", uploadDir);
//        } else {
//            log.info("uploadDir 설정값: {}", uploadDir);
//        }
//    }
//
//    public DiaryListRes findAll(DiaryGetReq req) {
//        int offset = (req.getCurrentPage() - 1) * req.getPageSize();
//        req.setOffset(offset);
//
//        List<DiaryGetRes> diaryList = diaryMapper.findAll(req);
//        int totalCount = diaryMapper.getTotalCount(req);
//
//        return new DiaryListRes(diaryList, totalCount);
//    }
//
//    public DiaryGetRes findById(int id, int memberId) {
//        DiaryGetRes diary = diaryMapper.findById(id);
//        if (diary == null) {
//            throw new CustomException("해당 일기가 존재하지 않습니다.", 404);
//        }
//        if (diary.getMemberNoLogin() != memberId) {
//            throw new CustomException("해당 일기에 접근할 수 없습니다.", 403);
//        }
//        return diary;
//    }
//
//    public DiaryPostAndUploadRes saveDiaryAndHandleUpload(int memberId, DiaryPostReq req) {
//        req.setMemberNoLogin(memberId);
//
//        UploadResponse uploadResponse = handleFileUpload(req.getDiaryImageFiles(), null);
//        if (uploadResponse != null) {
//            req.setImageFileName(uploadResponse.getSavedFileName());
//        }
//
//        diaryMapper.save(req);
//
//        return new DiaryPostAndUploadRes(
//                req.getId(),
//                uploadResponse != null ? List.of(uploadResponse) : Collections.emptyList()
//        );
//    }
//
//    public void updateDiary(DiaryPutReq req, int memberId) {
//        DiaryGetRes existing = validateAndGetDiary(req.getId(), memberId);
//        diaryMapper.modify(req);
//    }
//
//    public DiaryPostAndUploadRes updateDiaryAndHandleUpload(int memberId, DiaryPutReq req) {
//        DiaryGetRes existing = validateAndGetDiary(req.getId(), memberId);
//
//        UploadResponse uploadResponse = handleFileUpload(req.getDiaryImageFiles(), existing.getImageFileName());
//        if (uploadResponse != null) {
//            req.setImageFileName(uploadResponse.getSavedFileName());
//        } else {
//            req.setImageFileName(null);
//        }
//
//        diaryMapper.modify(req);
//
//        return new DiaryPostAndUploadRes(
//                req.getId(),
//                uploadResponse != null ? List.of(uploadResponse) : Collections.emptyList()
//        );
//    }
//
//    public void deleteDiary(int id, int memberId) {
//        DiaryGetRes existing = validateAndGetDiary(id, memberId);
//
//        if (existing.getImageFileName() != null) {
//            deleteFileIfExists(existing.getImageFileName());
//        }
//
//        diaryMapper.deleteById(id);
//    }
//
//    private DiaryGetRes validateAndGetDiary(int id, int memberId) {
//        DiaryGetRes existing = diaryMapper.findById(id);
//        if (existing == null) {
//            throw new CustomException("대상 일기가 존재하지 않습니다.", 404);
//        }
//        if (existing.getMemberNoLogin() != memberId) {
//            throw new CustomException("권한이 없습니다.", 403);
//        }
//        return existing;
//    }
//
//    private UploadResponse handleFileUpload(List<MultipartFile> imageFiles, String existingFileName) {
//        if (imageFiles != null && !imageFiles.isEmpty()) {
//            MultipartFile file = imageFiles.get(0);
//            if (file != null && !file.isEmpty()) {
//                if (existingFileName != null) {
//                    deleteFileIfExists(existingFileName);
//                }
//                String savedFileName = saveFile(file);
//                return new UploadResponse(savedFileName, file.getOriginalFilename(), "업로드 성공");
//            }
//        }
//        return null;
//    }
//
//    private String saveFile(MultipartFile file) {
//        try {
//            String originalFilename = file.getOriginalFilename();
//            String ext = (originalFilename != null && originalFilename.contains("."))
//                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
//                    : ".bin";
//            String safeFileName = UUID.randomUUID().toString() + ext;
//
//            Path target = Paths.get(uploadDir.trim()).resolve(safeFileName).normalize();
//            Files.createDirectories(target.getParent());
//            file.transferTo(target.toFile());
//
//            return safeFileName;
//        } catch (IOException e) {
//            log.error("파일 저장 실패: {}", e.getMessage());
//            throw new CustomException("파일 저장 중 오류가 발생했습니다.", 500);
//        }
//    }
//
//    private void deleteFileIfExists(String fileName) {
//        try {
//            Files.deleteIfExists(Paths.get(uploadDir.trim()).resolve(fileName).normalize());
//        } catch (IOException e) {
//            log.warn("파일 삭제 실패: {}", fileName);
//        }
//    }
//}