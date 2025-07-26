package com.otd.onetoday_back.memo;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.memo.config.model.ResultResponse;
import com.otd.onetoday_back.memo.model.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/OTD/memo")
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MemoController {

    private final MemoService memoService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResultResponse<MemoPostAnduploadRes> postMemo(
            HttpSession session,
            @RequestPart("memoData") MemoPostReq req,
            @RequestPart(value = "memoImageFiles", required = false) List<MultipartFile> memoImageFiles) {

        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            return ResultResponse.fail("로그인이 필요합니다.", null, 401);
        }

        req.setMemberNoLogin(memberId);
        req.setMemoImageFiles(memoImageFiles);

        log.info("📝 [메모 등록] userId: {}, title: {}, imageCount: {}",
                memberId, req.getTitle(), memoImageFiles != null ? memoImageFiles.size() : 0);

        MemoPostAnduploadRes result = memoService.saveMemoAndHandleUpload(memberId, req);
        return ResultResponse.ok("메모 등록 및 파일 업로드 성공", result);
    }

    @GetMapping
    public ResultResponse<MemoListRes> getMemo(@ModelAttribute MemoGetReq req, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            MemoListRes empty = MemoListRes.builder()
                    .memoList(Collections.emptyList())
                    .totalCount(0)
                    .build();
            return ResultResponse.fail("로그인이 필요합니다.", empty, 401); // ✅ data는 null 아님
        }

        req.setMemberNoLogin(memberId);
        MemoListRes result = memoService.findAll(req);
        return ResultResponse.ok("사용자 메모 조회 성공", result);
    }

    @GetMapping("{memoId}")
    public ResultResponse<MemoGetOneRes> getMemo(@PathVariable int memoId, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            return ResultResponse.fail("로그인이 필요합니다.", null, 401);
        }

        MemoGetOneRes result = memoService.findOwnedMemoById(memoId, memberId);
        return ResultResponse.ok("단일 메모 조회 성공", result);
    }

    @PutMapping
    public ResultResponse<Integer> putMemo(@RequestBody MemoPutReq req, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            return ResultResponse.fail("로그인이 필요합니다.", null, 401);
        }

        req.setMemberNoLogin(memberId); // 반드시 필요!
        int result = memoService.modify(req);
        return ResultResponse.ok("메모 수정 성공", result);
    }

    @DeleteMapping
    public ResultResponse<Integer> deleteMemo(@RequestParam(name = "id") int id, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            return ResultResponse.fail("로그인이 필요합니다.", null, 401);
        }

        int result = memoService.deleteById(id, memberId);
        return ResultResponse.ok("메모 삭제 성공", result);
    }
}