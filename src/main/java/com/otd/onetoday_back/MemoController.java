package com.otd.onetoday_back;

import com.otd.onetoday_back.config.model.ResultResponse;
import com.otd.onetoday_back.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/otd/memo")
public class MemoController {
    private final MemoService memoService;

//    @PostMapping
//    public ResultResponse<Integer> postMemo(@RequestBody MemoPostReq req) {
//        log.info("req={}", req);
//        int result = memoService.save(req);
//        return new ResultResponse<>("메모 저장 성공", result);
//    }
    @PostMapping(value = "/{userId}", consumes = {"multipart/form-data"})
    public ResultResponse<MemoPostAnduploadRes> postMemo(
            @PathVariable int userId,
            @RequestPart("req") MemoPostReq dto,
            @RequestPart(value = "memoImageFile", required = false) MultipartFile memoImageFile)
{
    log.info("userId:{}, req:{}, memoImageFile:{}",
            userId, dto, memoImageFile != null ? memoImageFile.getOriginalFilename() : "No file");
    dto.setMemoImageFile(memoImageFile);
    MemoPostAnduploadRes result = memoService.saveMemoAndHandleUpload(userId, dto);
    return new ResultResponse<>("메모 등록, 파일 업로드 성공", result);
}

    @GetMapping
    public ResultResponse<List<MemoGetRes>> getMemo(@ModelAttribute MemoGetReq req) {
        log.info("req={}", req);
        List<MemoGetRes> result = memoService.findAll(req);
        String message = String.format("rows: %d", result.size());
        return new ResultResponse<>(message, result);
    }
    @GetMapping("{memoId}")
    public ResultResponse<MemoGetOneRes> getMemo(@PathVariable int memoId) {
        log.info("memoId={}", memoId);
        MemoGetOneRes result = memoService.findById(memoId);
        return new ResultResponse<>("메모 조회 성공", result);
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
