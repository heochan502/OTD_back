package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.diary.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {

    int save(DiaryPostReq req);

    List<DiaryGetRes> findAll(DiaryGetReq req);

    int getTotalCount(DiaryGetReq req);

    DiaryGetRes findById(int id);

    int modify(DiaryPutReq req);

    int deleteById(int id);
}