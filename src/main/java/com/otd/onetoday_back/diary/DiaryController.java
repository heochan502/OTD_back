package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.common.model.ResultResponse;
import com.otd.onetoday_back.diary.model.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/memoAndDiary/diary")
public class DiaryController {

    private final DiaryService diaryService;

    private int getLoginMemberId(HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            throw new CustomException("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED.value());
        }
        return memberId;
    }

    @GetMapping
    public ResultResponse<DiaryListRes> findAll(DiaryGetReq req, HttpSession session) {
        req.setMemberNoLogin(getLoginMemberId(session));
        return ResultResponse.success(diaryService.findAll(req), "/api/OTD/memoAndDiary/diary");
    }

    @GetMapping("/{diaryId}")
    public ResultResponse<DiaryGetRes> findById(@PathVariable int diaryId, HttpSession session) {
        int memberId = getLoginMemberId(session);
        return ResultResponse.success(
                diaryService.findById(diaryId, memberId),
                "/api/OTD/memoAndDiary/diary/" + diaryId
        );
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<DiaryPostAndUploadRes> create(
            @RequestPart("diaryData") DiaryPostReq req,
            @RequestPart(value = "diaryImage", required = false) MultipartFile diaryImage,
            HttpSession session
    ) {
        int memberId = getLoginMemberId(session);
        req.setMemberNoLogin(memberId);
        DiaryPostAndUploadRes res = diaryService.save(req, diaryImage);
        return ResultResponse.success(res, "/api/OTD/memoAndDiary/diary");
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<DiaryPostAndUploadRes> update(
            @RequestPart("diaryData") DiaryPutReq req,
            @RequestPart(value = "diaryImage", required = false) MultipartFile imageFile,
            HttpSession session
    ) {
        int memberId = getLoginMemberId(session);
        req.setMemberNoLogin(memberId);
        return ResultResponse.success(diaryService.update(req, imageFile), "/api/OTD/memoAndDiary/diary");
    }

    @DeleteMapping("/{diaryId}")
    public ResultResponse<Void> delete(@PathVariable int diaryId, HttpSession session) {
        int memberId = getLoginMemberId(session);
        diaryService.delete(diaryId, memberId);
        return ResultResponse.success(null, "/api/OTD/memoAndDiary/diary/" + diaryId);
    }
}