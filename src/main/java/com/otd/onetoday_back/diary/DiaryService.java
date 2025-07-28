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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryMapper diaryMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public DiaryListRes findAll(DiaryGetReq req) {
        int offset = (req.getCurrentPage() - 1) * req.getPageSize();
        req.setOffset(offset);

        List<DiaryGetRes> diaryList = diaryMapper.findAll(req);
        int totalCount = diaryMapper.getTotalCount(req);

        return new DiaryListRes(diaryList, totalCount);
    }

    public DiaryGetRes findById(int id, int memberId) {
        DiaryGetRes diary = diaryMapper.findById(id);
        if (diary == null) {
            throw new CustomException("해당 일기가 존재하지 않습니다.", 404);
        }
        if (diary.getMemberNoLogin() != memberId) {
            throw new CustomException("해당 일기에 접근할 수 없습니다.", 403);
        }
        return diary;
    }

    public DiaryPostAndUploadRes saveDiaryAndHandleUpload(int memberId, DiaryPostReq req) {
        req.setMemberNoLogin(memberId);

        List<MultipartFile> imageFiles = req.getDiaryImageFiles();
        if (imageFiles != null && !imageFiles.isEmpty() && !imageFiles.get(0).isEmpty()) {
            MultipartFile file = imageFiles.get(0);
            String fileName = saveFile(file);
            req.setDiaryImage(fileName);
        }

        diaryMapper.save(req);
        int newDiaryId = req.getId();

        return new DiaryPostAndUploadRes(
                newDiaryId,
                req.getDiaryImage() != null
                        ? Collections.singletonList(new UploadResponse(
                        req.getDiaryImage(),
                        imageFiles.get(0).getOriginalFilename(),
                        "업로드 성공"))
                        : Collections.emptyList()
        );
    }

    public void updateDiary(DiaryPutReq req, int memberId) {
        DiaryGetRes existing = diaryMapper.findById(req.getId());
        if (existing == null) {
            throw new CustomException("수정할 일기가 존재하지 않습니다.", 404);
        }
        if (existing.getMemberNoLogin() != memberId) {
            throw new CustomException("수정 권한이 없습니다.", 403);
        }

        diaryMapper.modify(req);
    }

    public void deleteDiary(int id, int memberId) {
        DiaryGetRes existing = diaryMapper.findById(id);
        if (existing == null) {
            throw new CustomException("삭제할 일기가 존재하지 않습니다.", 404);
        }
        if (existing.getMemberNoLogin() != memberId) {
            throw new CustomException("삭제 권한이 없습니다.", 403);
        }

        if (existing.getDiaryImage() != null) {
            deleteFileIfExists(existing.getDiaryImage());
        }

        diaryMapper.deleteById(id);
    }

    private String saveFile(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String ext = (originalFilename != null && originalFilename.contains("."))
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : ".bin";

            String safeFileName = UUID.randomUUID().toString() + ext;
            Path target = Paths.get(uploadDir).resolve(safeFileName).normalize();

            Files.createDirectories(target.getParent());
            file.transferTo(target.toFile());
            return safeFileName;
        } catch (IOException e) {
            log.error("파일 저장 실패: {}", e.getMessage());
            throw new CustomException("파일 저장 중 오류가 발생했습니다.", 500);
        }
    }

    private void deleteFileIfExists(String fileName) {
        Path path = Paths.get(uploadDir).resolve(fileName).normalize();
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.warn("파일 삭제 실패: {}", fileName);
        }
    }
}