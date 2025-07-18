package com.otd.onetoday_back;

import com.otd.onetoday_back.config.model.ResultResponse;
import com.otd.onetoday_back.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoMapper memoMapper;

//    public int save(MemoPostReq req) { return memoMapper.save(req); }
    public MemoPostAnduploadRes saveMemoAndHandleUpload(int userId, MemoPostReq req) {
        int newMemoId = 111;
        UploadResponse uploadResponse = new UploadResponse("http://localhost:5173/otd/memo/file/abc.png", "abc.png", "파일 업로드 성공");
        return new MemoPostAnduploadRes(newMemoId, uploadResponse);
    }
    public List<MemoGetRes> findAll(MemoGetReq req) {
        return memoMapper.findAll(req);
    }
    public MemoGetOneRes findById(int id) {
        return memoMapper.findById(id);
    }
    public int modify(MemoPutReq req) {
        return memoMapper.modify(req);
    }
    public int deleteById(int id) {
        return memoMapper.deleteById(id);
    }
}
