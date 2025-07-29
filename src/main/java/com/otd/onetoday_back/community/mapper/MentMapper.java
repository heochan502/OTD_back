package com.otd.onetoday_back.community.mapper;

import com.otd.onetoday_back.community.domain.Ment;
import com.otd.onetoday_back.community.dto.MentReq;
import com.otd.onetoday_back.community.dto.MentRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MentMapper {
    void save(MentReq req);

    List<MentRes> findByPostId(@Param("postId") int postId);

    void deleteById(@Param("commentId") int commentId);
}
