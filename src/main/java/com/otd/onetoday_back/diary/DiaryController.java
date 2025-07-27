package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.common.model.ResultResponse;
import com.otd.onetoday_back.diary.model.DiaryGetRes;
import com.otd.onetoday_back.diary.model.DiaryPostAndUploadRes;
import com.otd.onetoday_back.diary.model.DiaryPostReq;
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
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class DiaryController {

    private final DiaryService diaryService;

    /**
     * 세션에서 로그인된 회원 ID를 추출 (없으면 예외)
     */
    private Integer getLoggedInMemberId(HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            throw new CustomException("로그인이 필요합니다.", 401);
        }
        return memberId;
    }

    /**
     * 일기 등록
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
     * 단일 일기 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse<DiaryGetRes>> getDiary(
            HttpSession session,
            @PathVariable int id,
            HttpServletRequest request
    ) {
        int memberId = getLoggedInMemberId(session);
        DiaryGetRes res = diaryService.findById(id, memberId);
        return ResponseEntity.ok(ResultResponse.success(res, request.getRequestURI()));
    }

    /**
     * 일기 수정
     */
    @PutMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ResultResponse<String>> updateDiary(
            HttpSession session,
            HttpServletRequest request,
            @RequestPart("diaryData") DiaryPostReq req,
            @RequestPart(value = "diaryImageFiles", required = false) List<MultipartFile> imageFiles
    ) {
        int memberId = getLoggedInMemberId(session);
        req.setDiaryImageFiles(imageFiles);
        diaryService.update(req, memberId);
        return ResponseEntity.ok(ResultResponse.success(null, request.getRequestURI()));
    }

    /**
     * 일기 삭제
     */
    @DeleteMapping
    public ResponseEntity<ResultResponse<String>> deleteDiary(
            HttpSession session,
            HttpServletRequest request,
            @RequestParam int id
    ) {
        int memberId = getLoggedInMemberId(session);
        diaryService.delete(id, memberId);
        return ResponseEntity.ok(ResultResponse.success(null, request.getRequestURI()));
    }

    /**
     * 일기 목록 조회 (페이지네이션)
     */
    @GetMapping
    public ResponseEntity<ResultResponse<List<DiaryGetRes>>> getDiaryList(
            HttpSession session,
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        int memberId = getLoggedInMemberId(session);
        List<DiaryGetRes> list = diaryService.findAll(memberId, page, size);
        return ResponseEntity.ok(ResultResponse.success(list, request.getRequestURI()));
    }
}