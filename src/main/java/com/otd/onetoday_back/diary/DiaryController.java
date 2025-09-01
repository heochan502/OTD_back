package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.common.model.ResultResponse;
import com.otd.onetoday_back.diary.model.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/memoAndDiary/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @Value("${constants.file.directory}")
    private String uploadDir;

    @Value("${upload.base-path:/home/download}")
    private String basePath;

    private int getLoginedMemberId(HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED.value());
        }
        return memberId;
    }

    @GetMapping
    public ResultResponse<DiaryListRes> findAll(DiaryGetReq req, HttpSession session) {

        Integer memberId = getLoginedMemberId(session);
        req.setMemberNoLogin(memberId);
        DiaryListRes result = diaryService.findAll(req);
        return ResultResponse.success(result, "/api/OTD/memoAndDiary/diary");
    }

    @GetMapping("/{diaryId}")
    public ResultResponse<DiaryGetRes> findById(@PathVariable int diaryId, HttpSession session) {
        int memberId = getLoginedMemberId(session);
        DiaryGetRes result = diaryService.findById(diaryId, memberId);
        return ResultResponse.success(result, "/api/OTD/memoAndDiary/diary/" + diaryId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<DiaryPostAndUploadRes> create(
            @RequestPart("diaryData") DiaryPostReq req,
            @RequestPart(value = "diaryImage", required = false) MultipartFile diaryImage,
            HttpSession session)
    {
        int memberId = getLoginedMemberId(session);
        req.setMemberNoLogin(memberId);
        DiaryPostAndUploadRes res = diaryService.save(req, diaryImage);
        String location = "/api/OTD/memoAndDiary/diary/" + res.getDiaryId();
        return ResultResponse.success(res, location);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<DiaryPostAndUploadRes> update(
            @RequestPart("diaryData") DiaryPutReq req,
            @RequestPart(value = "diaryImage", required = false) MultipartFile diaryImage,
            HttpSession session)
    {
        int memberId = getLoginedMemberId(session);
        req.setMemberNoLogin(memberId);
        DiaryPostAndUploadRes res = diaryService.update(req, diaryImage);
        String location = "/api/OTD/memoAndDiary/diary/" + res.getDiaryId();
        return ResultResponse.success(res, location);
    }

    @DeleteMapping("/{diaryId}")
    public ResultResponse<String> delete(@PathVariable int diaryId, HttpSession session) {
        int memberId = getLoginedMemberId(session);
        diaryService.delete(diaryId, memberId);
        String location = "/api/OTD/memoAndDiary/diary/" + diaryId;
        return ResultResponse.success("삭제 완료", location);
    }

    @GetMapping("/image/{filename}")
    public ResponseEntity<Resource> getDiaryImage(@PathVariable String filename) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String actualUploadDir = "C:/home/download";

            if(os.contains("win") && uploadDir != null &&  uploadDir.startsWith("C:/home/download/")) {
                actualUploadDir = uploadDir.substring("/home/download/".length());
            }
            Path filePath = Paths.get(actualUploadDir, "diary").resolve(filename).normalize();
            log.warn("이미지 요청 - 실제 찾는 경로: {}", filePath.toAbsolutePath());

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new CustomException("이미지를 찾을 수 없습니다.", 404);
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (IOException e) {
            throw new CustomException("이미지 로딩 중 오류 발생", 404);
        }
    }
}