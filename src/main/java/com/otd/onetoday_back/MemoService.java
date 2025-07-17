package com.otd.onetoday_back;

import com.otd.onetoday_back.config.model.ResultResponse;
import com.otd.onetoday_back.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoService memoService;

    public int save(MemoPostReq req) {
        return memoService.save(req);
    }
    public List<MemoGetRes> findAll(MemoGetReq req) {
        return memoService.findAll(req);
    }
    public MemoGetOneRes findById(int id) {
        return memoService.findById(id);
    }
    public int modify(MemoPutReq req) {
        return memoService.modify(req);
    }
    public MemoGetRes deleteById(int id) {
        return memoService.deleteById(id);
    }
}
