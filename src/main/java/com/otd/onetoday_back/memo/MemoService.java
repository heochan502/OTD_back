package com.otd.onetoday_back.memo;

import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.memo.model.*;
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
public class MemoService {
    private final MemoMapper memoMapper;

    @Value("${constants.file.directory}")
    private String uploadDir;

    public MemoPostAnduploadRes saveMemoAndHandleUpload(int userId, MemoPostReq req) {
        req.setMemberNoLogin(userId);
        memoMapper.save(req);
        int newMemoId = req.getId();

        List<UploadResponse> uploadResponseList = new ArrayList<>();
        List<MultipartFile> memoImageFiles = req.getMemoImageFiles();

        if (memoImageFiles != null && !memoImageFiles.isEmpty()) {
            try {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                    log.info("업로드 디렉토리 생성: {}", uploadPath.toAbsolutePath());
                }

                for (MultipartFile file : memoImageFiles) {
                    if (file == null || file.isEmpty()) continue;

                    String originalFilename = file.getOriginalFilename();
                    String fileExtension = "";

                    if (originalFilename != null && originalFilename.contains(".")) {
                        fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    }

                    String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
                    Path filePath = uploadPath.resolve(uniqueFileName);
                    Files.copy(file.getInputStream(), filePath);

                    uploadResponseList.add(new UploadResponse(uniqueFileName, originalFilename, "이미지 업로드 성공"));
                }

                if (!uploadResponseList.isEmpty()) {
                    String firstImageFileName = uploadResponseList.get(0).getFileName();
                    memoMapper.updateMemoImage(newMemoId, firstImageFileName);
                }

            } catch (IOException e) {
                log.error("이미지 업로드 실패: {}", e.getMessage(), e);
                uploadResponseList.add(new UploadResponse(null, null, "이미지 업로드 실패"));
            }
        } else {
            log.warn("업로드할 파일이 없거나 비어 있습니다");
            uploadResponseList.add(new UploadResponse(null, null, "파일 없음"));
        }

        return new MemoPostAnduploadRes(newMemoId, uploadResponseList);
    }

    public MemoListRes findAll(MemoGetReq req) {
        int actualCurrentPage = (req.getCurrentPage() > 0) ? req.getCurrentPage() : 1;
        int actualPageSize = (req.getPageSize() > 0) ? req.getPageSize() : 5;
        int offset = (actualCurrentPage - 1) * actualPageSize;

        req.setCurrentPage(actualCurrentPage);
        req.setPageSize(actualPageSize);
        req.setOffset(offset);

        MemoListRes response = new MemoListRes();
        List<MemoGetRes> memoList = memoMapper.findAll(req);
        int totalCount = memoMapper.getTotalCount(req);

        if (memoList != null) {
            response.setMemoList(memoList);
        }
        response.setTotalCount(totalCount);

        return response;
    }

    public MemoGetOneRes findOwnedMemoById(int memoId, int memberId) {
        MemoGetOneRes memo = Optional.ofNullable(memoMapper.findById(memoId))
                .orElseThrow(() -> new CustomException("해당 메모가 존재하지 않습니다.", 404));

        if (memo.getMemberNoLogin() != memberId) {
            throw new CustomException("권한이 없습니다.", 403);
        }

        return memo;
    }

    public int modify(MemoPutReq req) {
        MemoGetOneRes memo = Optional.ofNullable(memoMapper.findById(req.getId()))
                .orElseThrow(() -> new CustomException("해당 메모가 존재하지 않습니다.", 404));

        if (memo.getMemberNoLogin() != req.getMemberNoLogin()) {
            throw new CustomException("권한이 없습니다.", 403);
        }

        return memoMapper.modify(req);
    }

    public int deleteById(int memoId, int memberId) {
        MemoGetOneRes memo = Optional.ofNullable(memoMapper.findById(memoId))
                .orElseThrow(() -> new CustomException("해당 메모가 존재하지 않습니다.", 404));

        if (memo.getMemberNoLogin() != memberId) {
            throw new CustomException("권한이 없습니다.", 403);
        }

        return memoMapper.deleteById(memoId);
    }
}