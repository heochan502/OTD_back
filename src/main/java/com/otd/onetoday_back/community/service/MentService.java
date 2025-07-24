package com.otd.onetoday_back.community.service;

import com.otd.onetoday_back.community.dto.MentReq;
import com.otd.onetoday_back.community.dto.MentRes;
import com.otd.onetoday_back.community.mapper.MentMapper;
import com.otd.onetoday_back.community.domain.Ment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MentService {

    private final MentMapper mentMapper;

    // 댓글 저장
    public void saveMent(MentReq req) {
        Ment ment = Ment.builder()
                .postId(req.getPostId())
                .memberNoLogin(req.getMemberNoLogin())
                .content(req.getContent())
                .updatedAt(LocalDateTime.now())
                .build();

        mentMapper.save(ment);
    }

    // 게시글별 댓글 리스트 조회
    public List<MentRes> getMentListByPostId(int postId) {
        return mentMapper.findByPostId(postId);
    }

    // 댓글 삭제
    public void deleteMent(int mentId) {
        mentMapper.deleteById(mentId);
    }
}
