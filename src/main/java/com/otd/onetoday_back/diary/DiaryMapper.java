package com.otd.onetoday_back.diary;

import com.otd.onetoday_back.diary.model.DiaryGetRes;
import com.otd.onetoday_back.diary.model.DiaryPostReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiaryMapper {

    void save(DiaryPostReq req);
    DiaryGetRes findById(@Param("id") int id, @Param("memberNoLogin") int memberNoLogin);
    int update(DiaryPostReq req);
    int delete(@Param("id") int id, @Param("memberNoLogin") int memberNoLogin);
    List<DiaryGetRes> findAll(@Param("memberNoLogin") int memberNoLogin,
                              @Param("limit") int limit,
                              @Param("offset") int offset);
    int countAll(int memberNoLogin);
}