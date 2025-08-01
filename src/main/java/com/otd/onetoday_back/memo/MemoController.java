package com.otd.onetoday_back.memo;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.model.CustomException;
import com.otd.onetoday_back.common.model.ResultResponse;
import com.otd.onetoday_back.memo.model.*;
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
            @ModelAttribute MemoGetReq req,
            HttpSession session) {

        Integer memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);

        if (req.getCurrentPage() <= 0) req.setCurrentPage(1);
        if (req.getPageSize() <= 0) req.setPageSize(10);

        MemoListRes result = memoService.findAll(req);
        return ResultResponse.success(result, "/api/OTD/memoAndDiary/memo");
    }

    @GetMapping("/{id}")
    public ResultResponse<?> getMemoById(
            @PathVariable int id,
            HttpSession session) {

        Integer memberId = getLoggedInMemberId(session);
        MemoGetRes result = memoService.findById(id, memberId);
        return ResultResponse.success(result, "/api/OTD/memoAndDiary/memo/" + id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<?> postMemo(
            HttpSession session,
            @RequestPart("memoData") MemoPostReq req,
            @RequestPart(value = "memoImage", required = false) List<MultipartFile> memoImage) {

        Integer memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);
        req.setMemoImage(memoImage);

        MemoPostAnduploadRes result = memoService.saveMemoAndHandleUpload(memberId, req);
        String location = "/api/OTD/memoAndDiary/memo/" + result.getMemoId();
        return ResultResponse.success(result, location);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultResponse<?> updateMemo(
            HttpSession session,
            @RequestPart("memoData") MemoPutReq req,
            @RequestPart(value = "memoImageFiles", required = false) List<MultipartFile> memoImage) {

        Integer memberId = getLoggedInMemberId(session);
        req.setMemberNoLogin(memberId);
        req.setMemoImage(memoImage);

        MemoPostAnduploadRes result = memoService.updateMemo(req, memberId);
        String location = "/api/OTD/memoAndDiary/memo/" + result.getMemoId();
        return ResultResponse.success(result, location);
    }

    @DeleteMapping
    public ResultResponse<?> deleteMemo(
            @RequestParam("id") int id,
            HttpSession session) {

        Integer memberId = getLoggedInMemberId(session);
        memoService.deleteMemo(id, memberId);
        String location = "/api/OTD/memoAndDiary/memo/" + id;
        return ResultResponse.success("삭제 완료", location);
    }
}