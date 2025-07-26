package com.otd.onetoday_back.memo;
import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.memo.MemoService;
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

//    @PostMapping
//    public ResultResponse<Integer> postMemo(@RequestBody MemoPostReq req) {
//        log.info("req={}", req);
//        int result = memoService.save(req);
//        return new ResultResponse<>("메모 저장 성공", result);
//    }
    @PostMapping(consumes = {"multipart/form-data"})
    public ResultResponse<MemoPostAnduploadRes> postMemo(
        HttpSession session,
        @RequestPart("memoData") MemoPostReq req,
        @RequestPart(value = "memoImageFiles", required = false) List<MultipartFile> memoImageFiles) {

    Integer memberId = (Integer) session.getAttribute(AccountConstants.MEMBER_ID_NAME);
    if (memberId == null) {
        return new ResultResponse<>("로그인이 필요합니다.", null);
    }

    log.info("DEBUG: 세션 memberId: {}", memberId);
    log.info("DEBUG: req (title): {}, memoImageFiles: {}",
            req.getTitle(),
            (memoImageFiles != null)
                    ? memoImageFiles.stream().map(MultipartFile::getOriginalFilename).toList()
                    : "No files");

    req.setMemberNoLogin(memberId);
    req.setMemoImageFiles(memoImageFiles);
    MemoPostAnduploadRes result = memoService.saveMemoAndHandleUpload(memberId, req);
    return new ResultResponse<>("메모 등록, 파일 업로드 성공", result);
    }

    @GetMapping
    public ResultResponse<MemoListRes> getMemo(@ModelAttribute MemoGetReq req, HttpSession session) {
        Integer memberId = (Integer)session.getAttribute(AccountConstants.MEMBER_ID_NAME);
        if(memberId == null) {
            return new ResultResponse<>("로그인이 필요합니다.", null);
        }
        req.setMemberNoLogin(memberId);

        MemoListRes result = memoService.findAll(req);
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
    public ResultResponse<Integer> putMemo(@RequestBody MemoPutReq req) {
        log.info("req={}", req);
        int result = memoService.modify(req);
        return new ResultResponse<>("메모 수정 성공", result);
    }

    @DeleteMapping
    public ResultResponse<Integer> deleteMemo(@RequestParam(name = "id") int id) {
        log.info("id={}", id);
        int result = memoService.deleteById(id);
        return new ResultResponse<>("메모 삭제 성공", result);
    }
}
