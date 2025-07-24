package com.otd.onetoday_back.community.mapper;


import com.otd.onetoday_back.community.domain.CommunityLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommunityLikeMapper {
    CommunityLike findByPostIdAndMemberId(@Param("postId") int postId, @Param("memberId") int memberId);
    void insert(CommunityLike like);
    void deleteByPostIdAndMemberId(@Param("postId") int postId, @Param("memberId") int memberId);
    int countByPostId(@Param("postId") int postId);
    int like(@Param("postId") int postId, @Param("memberNoLogin") int memberNoLogin);
    void unlike(@Param("postId") int postId, @Param("memberNoLogin") int memberNoLogin);
    int exists(@Param("postId") int postId, @Param("memberNoLogin") int memberNoLogin);
    int countLikes(@Param("postId") int postId);

}
