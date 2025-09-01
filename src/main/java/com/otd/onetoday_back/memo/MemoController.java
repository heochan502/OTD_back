package com.otd.onetoday_back.memo;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.common.model.ResultResponse;
import com.otd.onetoday_back.memo.model.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/OTD/memoAndDiary/memo")
public class MemoController {

    private final MemoService memoService;

    @Value("${constants.file.directory}")
    private String uploadDir;

    @Value("${upload.base-path:/home/download}")
    private String basePath;

    private Integer getLoggedInMemberId(HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED.value());
        }
        return memberId;
    }

    @GetMapping
    public ResultResponse<MemoListRes> findAll(MemoGetReq req, HttpSession session) {

        Integer memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);
        MemoListRes result = memoService.findAll(req);
        return ResultResponse.success(result, "/api/OTD/memoAndDiary/memo");
    }

    @GetMapping("/{memoId}")
    public ResultResponse<MemoGetRes> findById(@PathVariable int memoId, HttpSession session) {
        int memberId = getLoggedInMemberId(session);
        MemoGetRes result = memoService.findById(memoId, memberId);
        return ResultResponse.success(result, "/api/OTD/memoAndDiary/memo/" + memoId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<MemoPostAnduploadRes> postMemo(
            @RequestPart("memoData") MemoPostReq req,
            @RequestPart(value = "memoImage", required = false) MultipartFile memoImage,
            HttpSession session)
    {

        int memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);
        MemoPostAnduploadRes res = memoService.save(req, memoImage);
        String location = "/api/OTD/memoAndDiary/memo/" + res.getMemoId();
        return ResultResponse.success(res, location);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<MemoPostAnduploadRes> update(
            @RequestPart("memoData") MemoPutReq req,
            @RequestPart(value = "memoImage", required = false) MultipartFile memoImage,
            HttpSession session)
    {
        int memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);
        MemoPostAnduploadRes res = memoService.update(req, memoImage);
        String location = "/api/OTD/memoAndDiary/memo/" + res.getMemoId();
        return ResultResponse.success(res, location);
    }
    @DeleteMapping("/{memoId}")
    public ResultResponse<String> delete(@PathVariable int memoId, HttpSession session) {
        int memberId = getLoggedInMemberId(session);
        memoService.delete(memoId, memberId);
        String location = "/api/OTD/memoAndDiary/memo/" + memoId;
        return ResultResponse.success("삭제 완료", location);
    }
    @GetMapping("/image/{filename}")
    public ResponseEntity<Resource> getMemoImage(@PathVariable String filename) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String actualUploadDir = "C:/home/download";

            if(os.contains("win") && uploadDir != null &&  uploadDir.startsWith("C:/home/download/")) {
                actualUploadDir = uploadDir.substring("/home/download/".length());
            }
            Path filePath = Paths.get(actualUploadDir, "memo").resolve(filename).normalize();
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