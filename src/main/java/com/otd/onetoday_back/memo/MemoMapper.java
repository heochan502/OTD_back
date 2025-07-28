package com.otd.onetoday_back.memo;

import com.otd.onetoday_back.memo.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemoMapper {

    // 메모 등록
    void save(MemoPostReq req);

    // 메모 목록 조회 (페이징)
    List<MemoGetRes> findAll(MemoGetReq req);

    // 총 개수 (페이징)
    int getTotalCount(MemoGetReq req);

    // 메모 단건 조회
    MemoGetRes findById(int id);

    // 메모 수정
    void modify(MemoPutReq req);

    // 메모 삭제
    void deleteById(int id);
}