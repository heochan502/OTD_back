package com.otd.onetoday_back.memo;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.memo.config.model.ResultResponse;
import com.otd.onetoday_back.memo.model.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/OTD/memo")
@CrossOrigin(origins = "http://localhost:5173")
public class MemoController {

    private final MemoService memoService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResultResponse<MemoPostAnduploadRes> postMemo(
            HttpSession session,
            @RequestPart("memoData") MemoPostReq req,
            @RequestPart(value = "memoImageFiles", required = false) List<MultipartFile> memoImageFiles) {

        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            return new ResultResponse<>("로그인이 필요합니다.", null);
        }

        req.setMemberNoLogin(memberId);
        req.setMemoImageFiles(memoImageFiles);

        log.info("\uD83D\uDD0D [Memo 등록] userId: {}, title: {}, imageCount: {}",
                memberId, req.getTitle(), memoImageFiles != null ? memoImageFiles.size() : 0);

        MemoPostAnduploadRes result = memoService.saveMemoAndHandleUpload(memberId, req);
        return new ResultResponse<>("메모 등록 및 파일 업로드 성공", result);
    }

    @GetMapping
    public ResultResponse<MemoListRes> getMemo(@ModelAttribute MemoGetReq req, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            return new ResultResponse<>("로그인이 필요합니다.", null);
        }
        req.setMemberNoLogin(memberId);
        MemoListRes result = memoService.findAll(req);
        if (result.getMemoList().isEmpty()) {
            log.info("사용자 {}의 등록된 메모가 없습니다.", memberId);
        }
        return new ResultResponse<>("사용자 메모 조회 성공", result);
    }

    @GetMapping("{memoId}")
    public ResultResponse<MemoGetOneRes> getMemo(@PathVariable int memoId, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            return new ResultResponse<>("로그인이 필요합니다.", null);
        }

        MemoGetOneRes result = memoService.findById(memoId);
        if (result == null) {
            return new ResultResponse<>("해당 메모가 존재하지 않습니다.", null);
        }
        if (!memberId.equals(result.getMemberNoLogin())) {
            return new ResultResponse<>("권한이 없습니다.", null);
        }

        return new ResultResponse<>("단일 메모 조회 성공", result);
    }

    @PutMapping
    public ResultResponse<Integer> putMemo(@RequestBody MemoPutReq req, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            return new ResultResponse<>("로그인이 필요합니다.", null);
        }

        log.info("\uD83D\uDCCB [메모 수정 요청] memoId: {}, title: {}", req.getId(), req.getTitle());
        int result = memoService.modify(req);
        return new ResultResponse<>("메모 수정 성공", result);
    }

    @DeleteMapping
    public ResultResponse<Integer> deleteMemo(@RequestParam(name = "id") int id, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            return new ResultResponse<>("로그인이 필요합니다.", null);
        }

        log.info("\u274C [메모 삭제 요청] memoId: {} by userId: {}", id, memberId);
        int result = memoService.deleteById(id);
        return new ResultResponse<>("메모 삭제 성공", result);
    }
}