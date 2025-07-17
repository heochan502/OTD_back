package com.otd.onetoday_back;

import com.otd.onetoday_back.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemoMapper {
    int save(MemoPostReq req);
    List<MemoGetRes> findAll(MemoGetReq req);
    MemoGetOneRes findById(int id);
    int modify(MemoPutReq req);
    int deleteById(int id);
}
