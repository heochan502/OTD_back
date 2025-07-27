package com.otd.onetoday_back.memo;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.common.model.ResultResponse;
import com.otd.onetoday_back.memo.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/memo")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MemoController {

    private final MemoService memoService;

    private Integer getLoggedInMemberId(HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            log.warn("세션에 로그인 정보가 없습니다.");
            throw new CustomException("로그인이 필요합니다.", 401);
        }
        return memberId;
    }

    /**
     * 메모 등록
     */
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ResultResponse<MemoPostAnduploadRes>> postMemo(
            HttpSession session,
            HttpServletRequest request,
            @RequestPart("memoData") MemoPostReq req,
            @RequestPart(value = "memoImageFiles", required = false) java.util.List<MultipartFile> imageFiles
    ) {
        int memberId = getLoggedInMemberId(session);
        req.setMemoImageFiles(imageFiles);
        MemoPostAnduploadRes res = memoService.saveMemoAndHandleUpload(memberId, req);
        return ResponseEntity.ok(ResultResponse.success(res, request.getRequestURI()));
    }

    /**
     * 메모 전체 목록 조회 (페이지네이션)
     */
    @GetMapping
    public ResponseEntity<ResultResponse<MemoListRes>> getMemoList(
            HttpSession session,
            HttpServletRequest request,
            @ModelAttribute MemoGetReq req
    ) {
        int memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);

        MemoListRes result = memoService.findAll(req);

        if (result == null) {
            log.warn("메모 목록 결과가 null입니다. 빈 목록으로 대체합니다.");
            result = new MemoListRes(); // 필드가 내부에서 null-safe 하게 초기화돼 있어야 함
        }

        return ResponseEntity.ok(ResultResponse.success(result, request.getRequestURI()));
    }

    /**
     * 메모 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse<MemoGetOneRes>> getMemoById(
            HttpSession session,
            HttpServletRequest request,
            @PathVariable int id
    ) {
        int memberId = getLoggedInMemberId(session);
        MemoGetOneRes memo = memoService.findOwnedMemoById(id, memberId);
        return ResponseEntity.ok(ResultResponse.success(memo, request.getRequestURI()));
    }

    /**
     * 메모 수정
     */
    @PutMapping
    public ResponseEntity<ResultResponse<String>> modifyMemo(
            HttpSession session,
            HttpServletRequest request,
            @RequestBody MemoPutReq req
    ) {
        int memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);
        memoService.modify(req);
        return ResponseEntity.ok(ResultResponse.success(null, request.getRequestURI()));
    }

    /**
     * 메모 삭제
     */
    @DeleteMapping
    public ResponseEntity<ResultResponse<String>> deleteMemo(
            HttpSession session,
            HttpServletRequest request,
            @RequestParam int id
    ) {
        int memberId = getLoggedInMemberId(session);
        memoService.deleteById(id, memberId);
        return ResponseEntity.ok(ResultResponse.success(null, request.getRequestURI()));
    }
}