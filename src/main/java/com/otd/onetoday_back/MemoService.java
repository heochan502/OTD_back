package com.otd.onetoday_back;

//import com.otd.onetoday_back.config.model.ResultResponse;
import com.otd.onetoday_back.model.*;
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
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoMapper memoMapper;

//    public int save(MemoPostReq req) { return memoMapper.save(req); }
    @Value("${file.upload-dir}")
    public String uploadDir;
    public MemoPostAnduploadRes saveMemoAndHandleUpload(int userId, MemoPostReq req) {
        int newMemoId = 111;

        UploadResponse uploadResponse;
        MultipartFile memoImageFile = req.getMemoImageFile();

        if(memoImageFile != null && !memoImageFile.isEmpty()) {
            try {
                Path uploadPath =  Paths.get(uploadDir);
                if(!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                    log.info("업로드 디렉토리 생성: {}", uploadPath.toAbsolutePath());
                }
                String originalFilename = memoImageFile.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
                Path filePath = uploadPath.resolve(uniqueFileName);

                Files.copy(memoImageFile.getInputStream(), filePath);

                String fileUrl = "/uploads/" + uniqueFileName;

                uploadResponse = new UploadResponse(fileUrl, originalFilename, "이미지 업로드 성공");
            } catch (IOException e) {
                log.error("이미지 업로드 실패: {}", e.getMessage(), e);
                uploadResponse = new UploadResponse(null, memoImageFile.getOriginalFilename(), "파일 업로드 실패: " + e.getMessage());
            }
        } else {
            log.error("업로드할 파일이 없거나 비어 있습니다");
            uploadResponse = new UploadResponse(null, null, "파일 없음");
        }
        return new MemoPostAnduploadRes(newMemoId, uploadResponse);
    }
    public List<MemoGetRes> findAll(MemoGetReq req) {
        return memoMapper.findAll(req);
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
