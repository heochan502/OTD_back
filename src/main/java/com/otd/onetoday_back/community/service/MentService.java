package com.otd.onetoday_back.community.service;

import com.otd.onetoday_back.community.dto.MentReq;
import com.otd.onetoday_back.community.dto.MentRes;
import com.otd.onetoday_back.community.mapper.MentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentService {

    private final MentMapper mentMapper;

    public List<MentRes> getMentsByPostId(int postId) {
        return mentMapper.findByPostId(postId);
    }

    @Transactional
    public MentRes create(MentReq req) {
        mentMapper.insert(req);                          // req.commentId 세팅됨
        return mentMapper.findByCommentId(req.getCommentId());
    }


    public boolean delete(long commentId, int memberNoLogin) {
        return mentMapper.deleteByIdAndMember(commentId, memberNoLogin) > 0;
    }
}
