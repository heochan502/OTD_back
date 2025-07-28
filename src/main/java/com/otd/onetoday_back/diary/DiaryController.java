package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.common.model.ResultResponse;
import com.otd.onetoday_back.diary.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/diary")
public class DiaryController {

    private final DiaryService diaryService;

    private Integer getLoggedInMemberId(HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            throw new CustomException("로그인 정보가 없습니다.", 401);
        }
        return memberId;
    }

    @GetMapping
    public ResultResponse<?> getDiaryList(
            HttpSession session,
            @ModelAttribute DiaryGetReq req,
            HttpServletRequest request) {

        Integer memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);

        DiaryListRes result = diaryService.findAll(req);
        return ResultResponse.success(result, request.getRequestURI());
    }

    @GetMapping("/{id}")
    public ResultResponse<?> getDiaryById(
            @PathVariable int id,
            HttpSession session,
            HttpServletRequest request) {

        Integer memberId = getLoggedInMemberId(session);
        DiaryGetRes result = diaryService.findById(id, memberId);
        return ResultResponse.success(result, request.getRequestURI());
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResultResponse<?> postDiary(
            HttpSession session,
            @RequestPart("diaryData") DiaryPostReq req,
            @RequestPart(value = "diaryImageFiles", required = false) List<MultipartFile> imageFiles,
            HttpServletRequest request) {

        Integer memberId = getLoggedInMemberId(session);
        req.setDiaryImageFiles(imageFiles);

        DiaryPostAndUploadRes result = diaryService.saveDiaryAndHandleUpload(memberId, req);
        return ResultResponse.success(result, request.getRequestURI());
    }

    @PutMapping
    public ResultResponse<?> updateDiary(
            HttpSession session,
            @RequestBody DiaryPutReq req,
            HttpServletRequest request) {

        Integer memberId = getLoggedInMemberId(session);
        diaryService.updateDiary(req, memberId);
        return ResultResponse.success("수정 완료", request.getRequestURI());
    }

    @DeleteMapping
    public ResultResponse<?> deleteDiary(
            @RequestParam("id") int id,
            HttpSession session,
            HttpServletRequest request) {

        Integer memberId = getLoggedInMemberId(session);
        diaryService.deleteDiary(id, memberId);
        return ResultResponse.success("삭제 완료", request.getRequestURI());
    }
}