package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.diary.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DiaryMapper {
    List<DiaryGetRes> findAll(DiaryGetReq req);
    int getTotalCount(DiaryGetReq req);

    DiaryGetRes findById(Map<String, Object> param);

    void save(DiaryPostReq req);
    void update(DiaryPutReq req);
    void delete(Map<String, Object> param);
}