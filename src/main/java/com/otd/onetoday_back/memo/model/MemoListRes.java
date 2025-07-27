package com.otd.onetoday_back.memo.model;

import java.util.ArrayList;
import java.util.List;

public class MemoListRes {
    private List<MemoGetRes> memoList;
    private int totalCount;

    public MemoListRes() {
        this.memoList = new ArrayList<>();
        this.totalCount = 0;
    }

    public List<MemoGetRes> getMemoList() {
        return memoList;
    }

    public void setMemoList(List<MemoGetRes> memoList) {
        this.memoList = memoList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}