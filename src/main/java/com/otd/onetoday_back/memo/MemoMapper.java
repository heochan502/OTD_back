package com.otd.onetoday_back.memo;

import com.otd.onetoday_back.memo.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemoMapper {

    // 메모 저장
    void save(MemoPostReq req);

    // 메모 전체 조회 (페이징 포함)
    List<MemoGetRes> findAll(MemoGetReq req);

    // 전체 개수 조회 (페이징을 위한 totalCount)
    int getTotalCount(MemoGetReq req);

    // 메모 상세 조회 (id + memberNoLogin)
    MemoGetRes findById(Map<String, Object> param);

    // 메모 수정
    void update(MemoPutReq req);

    // 메모 삭제
    void delete(Map<String, Object> param);
}