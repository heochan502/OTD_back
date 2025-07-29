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

    public void write(MentReq req) {
        mentMapper.save(req);
    }

    public List<MentRes> getComments(int postId) {
        return mentMapper.findByPostId(postId);
    }

    public void delete(int commentId) {
        mentMapper.deleteById(commentId);
    }

}
