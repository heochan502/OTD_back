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

    public DiaryGetRes findById(int id, int memberId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("memberNoLogin", memberId);

        DiaryGetRes diary = diaryMapper.findById(param);
        if (diary == null) {
            throw new CustomException("해당 다이어리를 찾을 수 없습니다.", 404);
        }
        return diary;
    }

    public DiaryPostAndUploadRes save(DiaryPostReq req, MultipartFile imageFile) {
        String uploadedFileName = uploadImage(imageFile);

        req.setDiaryImage(uploadedFileName);

        diaryMapper.save(req);

        return new DiaryPostAndUploadRes(req.getMemoId(), uploadedFileName);
    }

    public DiaryPostAndUploadRes update(DiaryPutReq req, MultipartFile imageFile) {
        DiaryGetRes existing = findById(req.getMemoId(), req.getMemberNoLogin());

        String uploadedFileName = uploadImage(imageFile);
        if (uploadedFileName != null) {
            deleteFileIfExists(existing.getDiaryImage());
            req.setDiaryImage(uploadedFileName);
        } else {
            req.setDiaryImage(existing.getDiaryImage());
        }

        diaryMapper.update(req);

        return new DiaryPostAndUploadRes(req.getMemoId(), req.getDiaryImage());
    }


    public void delete(int id, int memberId) {
        DiaryGetRes existing = findById(id, memberId);
        deleteFileIfExists(existing.getDiaryImage());

        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("memberNoLogin", memberId);

        diaryMapper.delete(param);
    }

    private String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        String extension = getExtension(file.getOriginalFilename());
        String uuidFileName = UUID.randomUUID() + "." + extension;

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path fullPath = uploadPath.resolve(uuidFileName);
            file.transferTo(fullPath.toFile());

            return uuidFileName;
        } catch (IOException e) {
            throw new CustomException("이미지 업로드 실패: " + e.getMessage(), 500);
        }
    }

    private void deleteFileIfExists(String fileName) {
        if (fileName == null || fileName.isEmpty()) return;

        Path path = Paths.get(uploadDir, fileName);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("이미지 삭제 실패: {}", fileName);
        }
    }

    private String getExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) return "jpg";
        return originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
    }
}