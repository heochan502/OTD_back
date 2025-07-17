package com.otd.onetoday_back;

import com.otd.onetoday_back.model.MemoPostReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoService memoService;

    public int save(MemoPostReq req) {
        return memoService.save(req);
    }
}
