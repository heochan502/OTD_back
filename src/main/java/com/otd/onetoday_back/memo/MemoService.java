package com.otd.onetoday_back.memo;

import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.memo.model.*;
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
public class MemoService {

    private final MemoMapper memoMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public MemoListRes findAll(MemoGetReq req) {
        int offset = (req.getCurrentPage() - 1) * req.getPageSize();
        req.setOffset(offset);

        List<MemoGetRes> memoList = memoMapper.findAll(req);
        int totalCount = memoMapper.getTotalCount(req);

        return new MemoListRes(memoList, totalCount);
    }

    public MemoGetRes findById(int id, int memberId) {
        MemoGetRes memo = memoMapper.findById(id);
        if (memo == null) {
            throw new CustomException("해당 메모가 존재하지 않습니다.", 404);
        }
        if (memo.getMemberNoLogin() != memberId) { // ← 여기 memo로 수정
            throw new CustomException("해당 메모에 접근할 수 없습니다.", 403);
        }

        return memo;
    }

    public MemoPostAnduploadRes saveMemoAndHandleUpload(int memberId, MemoPostReq req) {
        req.setMemberNoLogin(memberId);

        List<MultipartFile> imageFiles = req.getMemoImageFiles();
        if (imageFiles != null && !imageFiles.isEmpty() && !imageFiles.get(0).isEmpty()) {
            MultipartFile file = imageFiles.get(0);
            String fileName = saveFile(file);
            req.setMemoImage(fileName); // DB에 저장할 대표 이미지 파일명
        }

        memoMapper.save(req);
        int newMemoId = req.getId();

        return new MemoPostAnduploadRes(
                newMemoId,
                req.getMemoImage() != null
                        ? Collections.singletonList(new UploadResponse(
                        req.getMemoImage(),
                        imageFiles.get(0).getOriginalFilename(),
                        "업로드 성공"))
                        : Collections.emptyList()
        );
    }

    public void updateMemo(MemoPutReq req, int memberId) {
        MemoGetRes existing = memoMapper.findById(req.getId());
        if (existing == null) {
            throw new CustomException("수정할 메모가 존재하지 않습니다.", 404);
        }
        if (existing.getMemberNoLogin() != memberId) {
            throw new CustomException("수정 권한이 없습니다.", 403);
        }

        // memoImage 포함하여 업데이트
        memoMapper.modify(req);
    }

    public void deleteMemo(int id, int memberId) {
        MemoGetRes existing = memoMapper.findById(id);
        if (existing == null) {
            throw new CustomException("삭제할 메모가 존재하지 않습니다.", 404);
        }
        if (existing.getMemberNoLogin() != memberId) {
            throw new CustomException("삭제 권한이 없습니다.", 403);
        }

        if (existing.getMemoImage() != null) {
            deleteFileIfExists(existing.getMemoImage());
        }

        memoMapper.deleteById(id);
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
            file.transferTo(target.toFile()); // 또는 Files.copy(file.getInputStream(), target);
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