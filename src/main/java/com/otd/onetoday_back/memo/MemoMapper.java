package com.otd.onetoday_back.memo;

import com.otd.onetoday_back.memo.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemoMapper {

    void save(MemoPostReq req);

    MemoGetRes findById(int id);

    void modify(MemoPutReq req);

    void deleteById(int id);

    List<MemoGetRes> findAll(MemoGetReq req); // 페이징 포함 리스트 조회

    int countAll(MemoGetReq req); // 전체 개수 조회

    int getTotalCount(MemoGetReq req);
}