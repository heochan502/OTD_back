// com.otd.onetoday_back.community.mapper.MentMapper
package com.otd.onetoday_back.community.mapper;

import com.otd.onetoday_back.community.dto.MentReq;
import com.otd.onetoday_back.community.dto.MentRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MentMapper {
    List<MentRes> findByPostId(@Param("postId") int postId);

    int insert(MentReq req); // XML에서 useGeneratedKeys로 commentId 세팅

    MentRes findByCommentId(@Param("commentId") long commentId); // ✅ 추가

    int deleteByIdAndMember(@Param("commentId") long commentId,
                            @Param("memberNoLogin") int memberNoLogin);
}
