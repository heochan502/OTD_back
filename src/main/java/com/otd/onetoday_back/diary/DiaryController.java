package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.common.model.ResultResponse;
import com.otd.onetoday_back.diary.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/diary")
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class DiaryController {

    private final DiaryService diaryService;

    private Integer getLoggedInMemberId(HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            log.warn("세션에 로그인 정보가 없습니다.");
            throw new CustomException("로그인이 필요합니다.", 401);
        }
        return memberId;
    }

    /**
     * 일기 등록 (이미지 포함)
     */
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ResultResponse<DiaryPostAndUploadRes>> postDiary(
            HttpSession session,
            HttpServletRequest request,
            @RequestPart("diaryData") DiaryPostReq req,
            @RequestPart(value = "diaryImageFiles", required = false) List<MultipartFile> imageFiles
    ) {
        int memberId = getLoggedInMemberId(session);
        req.setDiaryImageFiles(imageFiles);
        DiaryPostAndUploadRes res = diaryService.saveDiaryAndHandleUpload(memberId, req);
        return ResponseEntity.ok(ResultResponse.success(res, request.getRequestURI()));
    }

    /**
     * 일기 목록 조회 (페이징)
     */
    @GetMapping
    public ResponseEntity<ResultResponse<DiaryListRes>> getDiaryList(
            HttpSession session,
            HttpServletRequest request,
            @ModelAttribute DiaryGetReq req
    ) {
        int memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);
        DiaryListRes result = diaryService.findAll(req);
        return ResponseEntity.ok(ResultResponse.success(result, request.getRequestURI()));
    }

    /**
     * 일기 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse<DiaryGetRes>> getDiaryById(
            HttpSession session,
            HttpServletRequest request,
            @PathVariable int id
    ) {
        int memberId = getLoggedInMemberId(session);
        DiaryGetRes diary = diaryService.findOwnedDiaryById(id, memberId);
        return ResponseEntity.ok(ResultResponse.success(diary, request.getRequestURI()));
    }

    /**
     * 일기 수정
     */
    @PutMapping
    public ResponseEntity<ResultResponse<Void>> modifyDiary(
            HttpSession session,
            HttpServletRequest request,
            @RequestBody DiaryPutReq req
    ) {
        int memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);
        diaryService.modify(req);
        return ResponseEntity.ok(ResultResponse.success(null, request.getRequestURI()));
    }

    /**
     * 일기 삭제
     */
    @DeleteMapping
    public ResponseEntity<ResultResponse<Void>> deleteDiary(
            HttpSession session,
            HttpServletRequest request,
            @RequestParam int id
    ) {
        int memberId = getLoggedInMemberId(session);
        diaryService.deleteById(id, memberId);
        return ResponseEntity.ok(ResultResponse.success(null, request.getRequestURI()));
    }
}