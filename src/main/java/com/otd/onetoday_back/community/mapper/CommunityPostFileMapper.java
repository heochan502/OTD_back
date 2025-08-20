package com.otd.onetoday_back.community.mapper;

import com.otd.onetoday_back.community.domain.CommunityPostFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommunityPostFileMapper {
    int insert(CommunityPostFile file);
    List<CommunityPostFile> findByPostId(@Param("postId") int postId);
    int deleteByPostId(@Param("postId") int postId);
}
