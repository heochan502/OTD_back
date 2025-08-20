package com.otd.onetoday_back.community.mapper;

import com.otd.onetoday_back.community.domain.CommunityPostFile;
import com.otd.onetoday_back.community.dto.CommunityPostReq;
import com.otd.onetoday_back.community.dto.CommunityPostRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CommunityMapper {

    void save(CommunityPostReq req);

    List<CommunityPostRes> findAll(@Param("searchText") String searchText);

    CommunityPostRes findById(@Param("postId") int postId);

    void modify(
            @Param("postId") int postId,
            @Param("title") String title,
            @Param("content") String content,
            @Param("updatedAt") LocalDateTime updatedAt
    );


    // 대표이미지 컬럼만 담당 (community_post.file_path 업데이트)
    void updatePostFilePath(@Param("postId") int postId, @Param("filePath") String filePath);

    void deleteById(int postId);

    int countCommentsByPostId(@Param("postId") int postId);

    void toggleLike(@Param("postId") int postId);

    int updateLikeCount(@Param("postId") int postId, @Param("likeCount") int likeCount);

    List<CommunityPostRes> findAllWithPaging(
            @Param("searchText") String searchText,
            @Param("size") int size,
            @Param("offset") int offset
    );

    int countAll(@Param("searchText") String searchText);

    void deleteCommentsByPostId(@Param("postId") int postId);
    void deleteLikesByPostId(@Param("postId") int postId);

    CommunityPostRes findPostById(@Param("postId") int postId);

    // 게시글 첨부파일 목록 조회
    List<CommunityPostFile> findFilesByPostId(@Param("postId") int postId);
}
