package com.otd.onetoday_back.community.mapper;


import com.otd.onetoday_back.community.domain.CommunityLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommunityLikeMapper {
    CommunityLike findByPostIdAndMemberId(@Param("postId") int postId, @Param("memberId") int memberId); // ❓ 필요 없다면 제거 가능
    void like(@Param("postId") int postId, @Param("memberId") int memberId);
    void unlike(@Param("postId") int postId, @Param("memberId") int memberId);
    int exists(@Param("postId") int postId, @Param("memberId") int memberId);
    int countLikes(@Param("postId") int postId);
    int updateLikeCount(@Param("postId") int postId, @Param("likeCount") int likeCount);
}

