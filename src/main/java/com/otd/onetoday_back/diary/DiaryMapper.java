package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.diary.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiaryMapper {

    void save(DiaryPostReq req);

    List<DiaryGetRes> findAll(DiaryGetReq req);

    int getTotalCount(DiaryGetReq req);

    DiaryGetRes findById(int id); // memberId 체크는 서비스단에서 처리

    void modify(DiaryPutReq req);

    void deleteById(int id);
}