package com.otd.onetoday_back.community.mapper;

import com.otd.onetoday_back.community.domain.Ment;
import com.otd.onetoday_back.community.dto.MentRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MentMapper {
    void save(Ment ment);
    List<MentRes> findByPostId(int postId);
    void deleteById(int mentId);
}
