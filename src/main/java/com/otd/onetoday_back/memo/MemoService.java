package com.otd.onetoday_back.memo;

import com.otd.onetoday_back.memo.model.*;
import lombok.RequiredArgsConstructor;
//import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor

public class MemoService {
    @Value("${constants.file.directory}")
    private String uploadDir;

    private static final String IMAGE_BASE_URL = "/uploads/";

    private final MemoMapper memoMapper;

//    public int save(MemoPostReq req) { return memoMapper.save(req); }

    public MemoPostAnduploadRes saveMemoAndHandleUpload(int userId, MemoPostReq req) {
        req.setMemberNoLogin(userId);

        memoMapper.save(req);
        int newMemoId = req.getId();

        List<UploadResponse> uploadResponseList = new ArrayList<>();
        List<MultipartFile> memoImageFiles = req.getMemoImageFiles();

        List<String> uploadedUniqueFileNameForDb = new ArrayList<>();

        if(memoImageFiles != null && !memoImageFiles.isEmpty()) {
            try {
                Path uploadPath =  Paths.get(uploadDir);
                if(!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                    log.info("업로드 디렉토리 생성: {}", uploadPath.toAbsolutePath());
                }
                // 업로드된 파일 이름을 저장할 리스트 생성
                for(MultipartFile file : memoImageFiles) {
                    if (file == null || file.isEmpty()) continue;

                    String originalFilename = file.getOriginalFilename();
                    String fileExtension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                    String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
                    Path filePath = uploadPath.resolve(uniqueFileName);
                    Files.copy(file.getInputStream(), filePath);

                    String fileUrl = IMAGE_BASE_URL + uniqueFileName;
                    uploadResponseList.add(new UploadResponse(fileUrl, originalFilename, uniqueFileName, "이미지 업로드 성공"));
                    uploadedUniqueFileNameForDb.add(uniqueFileName);
                }
                if(!uploadedUniqueFileNameForDb.isEmpty()) {
                    String firstUniqueFileNameForDb = uploadedUniqueFileNameForDb.get(0);
                    memoMapper.updateMemoImage(newMemoId, firstUniqueFileNameForDb);
                    log.info("메모 {}에 첫 번째 이미지 {}를 업데이트했습니다.", newMemoId, firstUniqueFileNameForDb);
                } else {
                    log.info("업로드된 유효한 이미지 파일이 없습니다. 메모 ID: {}", newMemoId);
                }

            } catch (IOException e) {
                log.error("이미지 업로드 처리 중 오류 발생: {}", newMemoId, e.getMessage(), e);
                uploadResponseList.add(new UploadResponse(null, null, null, "이미지 업로드 실패"));
            }
            } else {
            log.info("업로드할 파일이 없거나 비어 있습니다. 메모 ID {}", newMemoId);
            }
            return new MemoPostAnduploadRes(newMemoId, uploadResponseList);
            }

    public MemoListRes findAll(MemoGetReq req) {
        int actualCurrentPage = (req.getCurrentPage() != null && req.getCurrentPage() > 0) ? req.getCurrentPage() : 1;
        int actualPageSize = (req.getPageSize() != null && req.getPageSize() > 0) ? req.getPageSize() : 5; // 기본값을 5로 설정

        // Offset
        int offset = (actualCurrentPage - 1) * actualPageSize;

        // MemoGetReq 객체 실제 사용 값
        req.setCurrentPage(actualCurrentPage);
        req.setPageSize(actualPageSize);
        req.setOffset(offset);

        List<MemoGetRes> memoList = memoMapper.findAll(req);
        int totalCount = memoMapper.getTotalCount(req);

        return new MemoListRes(memoList, totalCount);
    }
    public MemoGetOneRes findById(int id) {
        return memoMapper.findById(id);
    }
    public int modify(MemoPutReq req) {
        return memoMapper.modify(req);
    }
    public int deleteById(int id) {
        return memoMapper.deleteById(id);
    }
}
