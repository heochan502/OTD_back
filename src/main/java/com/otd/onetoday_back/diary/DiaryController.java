package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.common.model.ResultResponse;
import com.otd.onetoday_back.diary.model.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/memoAndDiary/diary")
public class DiaryController {

    private final DiaryService diaryService;

    // ✅ 공통 로그인 검사
    private Integer getLoginMemberId(HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            // 401 Unauthorized
            throw new CustomException("로그인이 필요합니다.", 401);
        }
        return memberId;
    }

    @GetMapping
    public ResultResponse<DiaryListRes> findAll(@ModelAttribute DiaryGetReq req, HttpSession session) {
        Integer memberId = getLoginMemberId(session);
        req.setMemberNoLogin(memberId);
        return ResultResponse.success(diaryService.findAll(req), "/api/OTD/diary");
    }

    @GetMapping("/{id}")
    public ResultResponse<DiaryGetRes> findById(@PathVariable int id, HttpSession session) {
        Integer memberId = getLoginMemberId(session);
        return ResultResponse.success(diaryService.findById(id, memberId), "/api/OTD/diary/" + id);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResultResponse<DiaryPostAndUploadRes> postDiary(
            HttpSession session,
            @RequestPart("diaryData") DiaryPostReq req,
            @RequestPart(value = "diaryImageFiles", required = false) List<MultipartFile> diaryImageFiles) {

        Integer memberId = getLoginMemberId(session);
        req.setDiaryImageFiles(diaryImageFiles);
        return ResultResponse.success(diaryService.saveDiaryAndHandleUpload(memberId, req), "/api/OTD/diary");
    }

    @PutMapping(consumes = {"application/json"})
    public ResultResponse<String> putDiary(
            HttpSession session,
            @RequestBody DiaryPutReq req) {

        Integer memberId = getLoginMemberId(session);
        diaryService.updateDiary(req, memberId);
        return ResultResponse.success("ok", "/api/OTD/diary");
    }

    @PutMapping(consumes = {"multipart/form-data"})
    public ResultResponse<?> updateDiaryWithImage(
            HttpSession session,
            @RequestPart("diaryData") DiaryPutReq req,
            @RequestPart(value = "diaryImageFiles", required = false) List<MultipartFile> diaryImageFiles
    ) {
        Integer memberId = getLoginMemberId(session);
        req.setDiaryImageFiles(diaryImageFiles);
        DiaryPostAndUploadRes result = diaryService.updateDiaryAndHandleUpload(memberId, req);
        return ResultResponse.success(result, "/api/OTD/diary");
    }

    @DeleteMapping
    public ResultResponse<String> deleteDiary(
            @RequestParam int id,
            HttpSession session) {

        Integer memberId = getLoginMemberId(session);
        diaryService.deleteDiary(id, memberId);
        return ResultResponse.success("ok", "/api/OTD/diary");
    }
}