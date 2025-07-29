package com.otd.onetoday_back.community.mapper;

import com.otd.onetoday_back.community.dto.CommunityPostReq;
import com.otd.onetoday_back.community.dto.CommunityPostRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CommunityMapper {
    // 새 게시글을 저장(INSERT)하기 위한 것
    // @Param("이름") 은 이 메서드를 사용하는 MyBatis XML
    // (CommunityMapper.xml)에서 #{이름} 으로 매핑할 수 있게 해주는 역할
    void save(CommunityPostReq req);

    List<CommunityPostRes> findAll(@Param("searchText") String searchText);

    CommunityPostRes findById(@Param("postId") int postId);

    void modify(
            @Param("postId") int postId,
            @Param("title") String title,
            @Param("content") String content,
            @Param("updatedAt") LocalDateTime updatedAt
    );

    // 파일 저장용 메서드 추가
    void saveFile(
            @Param("postId") int postId,
            @Param("filePath") String filePath
    );

    void deleteById(int postId);

    // 댓글 수 조회용 메서드 추가
    int countCommentsByPostId(@Param("postId") int postId);

    // 좋아요 토글
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
    void deleteFilesByPostId(@Param("postId") int postId);

    CommunityPostRes findPostById(@Param("postId") int postId);


}