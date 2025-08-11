package com.otd.onetoday_back.community.service;

import com.otd.onetoday_back.community.dto.MentRes;
import com.otd.onetoday_back.community.mapper.MentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentService {

    private final MentMapper mentMapper;

    public List<MentRes> getMentsByPostId(int postId) {
        return mentMapper.findByPostId(postId);
    }
}
