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

    // 단건 첨부파일 조회 (삭제 시 권한/대표이미지 판별용)
    CommunityPostFile findById(@Param("fileId") int fileId);

    // 단건 첨부파일 삭제
    int deleteById(@Param("fileId") int fileId);
}
