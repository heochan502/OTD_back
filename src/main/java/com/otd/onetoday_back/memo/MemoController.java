package com.otd.onetoday_back.memo;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.common.model.ResultResponse;
import com.otd.onetoday_back.memo.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/OTD/memoAndDiary/memo")
public class MemoController {

    private final MemoService memoService;

    private Integer getLoggedInMemberId(HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            throw new CustomException("로그인 정보가 없습니다.", 401);
        }
        return memberId;
    }

    @GetMapping
    public ResultResponse<?> getMemoList(
            HttpSession session,
            @ModelAttribute MemoGetReq req,
            HttpServletRequest request) {

        Integer memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);

        if (req.getCurrentPage() <= 0) req.setCurrentPage(1);
        if (req.getPageSize() <= 0) req.setPageSize(10);

        MemoListRes result = memoService.findAll(req);
        return ResultResponse.success(result, request.getRequestURI());
    }

    @GetMapping("/{id}")
    public ResultResponse<?> getMemoById(
            @PathVariable int id,
            HttpSession session,
            HttpServletRequest request) {

        Integer memberId = getLoggedInMemberId(session);
        MemoGetRes result = memoService.findById(id, memberId);
        return ResultResponse.success(result, request.getRequestURI());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<?> postMemo(
            HttpSession session,
            @RequestPart("memoData") MemoPostReq req,
            @RequestPart(value = "memoImageFiles", required = false) List<MultipartFile> memoImageFiles,
            HttpServletRequest request) {

        Integer memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);
        req.setMemoImageFiles(memoImageFiles);

        MemoPostAnduploadRes result = memoService.saveMemoAndHandleUpload(memberId, req);
        return ResultResponse.success(result, request.getRequestURI());
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<?> updateMemo(
            HttpSession session,
            @RequestPart("memoData") MemoPutReq req,
            @RequestPart(value = "memoImageFiles", required = false) List<MultipartFile> memoImageFiles,
            HttpServletRequest request) {

        Integer memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);
        req.setMemoImageFiles(memoImageFiles);

        memoService.updateMemo(req, memberId);
        return ResultResponse.success("수정 완료", request.getRequestURI());
    }

    @DeleteMapping
    public ResultResponse<?> deleteMemo(
            @RequestParam("id") int id,
            HttpSession session,
            HttpServletRequest request) {

        Integer memberId = getLoggedInMemberId(session);
        memoService.deleteMemo(id, memberId);
        return ResultResponse.success("삭제 완료", request.getRequestURI());
    }
}