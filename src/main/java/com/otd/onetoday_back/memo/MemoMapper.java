package com.otd.onetoday_back.memo;

import com.otd.onetoday_back.memo.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemoMapper {
    void save(MemoPostReq req);
    void updateMemoImage(@Param("memoId") int memoId, @Param("memoImage") String memoImage);
    List<String> findImagesByMemoId(int memoId);
    List<MemoGetRes> findAll(MemoGetReq req);
    int getTotalCount(MemoGetReq req);
    MemoGetOneRes findById(int id);
    int modify(MemoPutReq req);
    int deleteById(int id);
}
