package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.common.model.ResultResponse;
import com.otd.onetoday_back.diary.model.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/memoAndDiary/diary")
public class DiaryController {

    private final DiaryService diaryService;

    private int getLoginMemberId(HttpSession session) {
        Integer memberId = (Integer) session.getAttribute("loginMemberId");
        if (memberId == null) {
            throw new CustomException("로그인이 필요합니다.", 401);
        }
        return memberId;
    }

    // ✅ 다이어리 목록 조회
    @GetMapping
    public ResultResponse<DiaryListRes> findAll(DiaryGetReq req, HttpSession session) {
        req.setMemberNoLogin(getLoginMemberId(session));
        return ResultResponse.success(diaryService.findAll(req), "/api/OTD/memoAndDiary/diary");
    }

    // ✅ 단일 조회
    @GetMapping("/{id}")
    public ResultResponse<DiaryGetRes> findById(@PathVariable int id, HttpSession session) {
        int memberId = getLoginMemberId(session);
        return ResultResponse.success(diaryService.findById(id, memberId), "/api/OTD/memoAndDiary/diary/" + id);
    }

    // ✅ 등록 (multipart/form-data 처리)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<DiaryPostAndUploadRes> create(
            @RequestPart("diaryData") DiaryPostReq req,
            @RequestPart(value = "diaryImageFiles", required = false) MultipartFile imageFile,
            HttpSession session
    ) {
        int memberId = getLoginMemberId(session);
        req.setMemberNoLogin(memberId);
        return ResultResponse.success(diaryService.save(req, imageFile), "/api/OTD/memoAndDiary/diary");
    }

    // ✅ 수정 (multipart/form-data 처리)
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<DiaryPostAndUploadRes> update(
            @RequestPart("diaryData") DiaryPutReq req,
            @RequestPart(value = "diaryImageFiles", required = false) MultipartFile imageFile,
            HttpSession session
    ) {
        int memberId = getLoginMemberId(session);
        req.setMemberNoLogin(memberId);
        return ResultResponse.success(diaryService.update(req, imageFile), "/api/OTD/memoAndDiary/diary");
    }

    // ✅ 삭제
    @DeleteMapping("/{id}")
    public ResultResponse<Void> delete(@PathVariable int id, HttpSession session) {
        int memberId = getLoginMemberId(session);
        diaryService.delete(id, memberId);
        return ResultResponse.success(null, "/api/OTD/memoAndDiary/diary/" + id);
    }
}