package com.otd.onetoday_back.community.service;

import com.otd.onetoday_back.community.dto.CommunityListRes;
import com.otd.onetoday_back.community.dto.CommunityPostReq;
import com.otd.onetoday_back.community.dto.CommunityPostRes;
import com.otd.onetoday_back.community.mapper.CommunityLikeMapper;
import com.otd.onetoday_back.community.mapper.CommunityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.otd.onetoday_back.community.domain.CommunityLike;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityLikeMapper likeMapper;
    private final CommunityMapper communityMapper;

    // 게시글 등록
    public void createPost(CommunityPostReq req) {
        req.setCreatedAt(LocalDateTime.now());
        req.setUpdatedAt(LocalDateTime.now());
        req.setViewCount(0);
        req.setLike(0);
        req.setFilePath(null); // 파일 업로드 제외하므로 null

        communityMapper.save(req); // DTO 한 개만 전달
    }

    // Service
    public void toggleLike(int postId, int memberNoLogin) {
        if (likeMapper.exists(postId, memberNoLogin) > 0) {
            likeMapper.unlike(postId, memberNoLogin);
        } else {
            likeMapper.like(postId, memberNoLogin);
        }
        int count = likeMapper.countLikes(postId);
        communityMapper.updateLikeCount(postId, count);
    }


    public CommunityPostRes getPostById(int postId, int memberId) {
        CommunityPostRes res = communityMapper.findById(postId);
        CommunityLike like = likeMapper.findByPostIdAndMemberId(postId, memberId);
        res.setLikedByCurrentUser(like != null); // 프론트로 좋아요 여부 전달
        return res;
    }

    // 게시글 목록 조회
    public List<CommunityPostRes> getAllPosts(String searchText) {
        return communityMapper.findAll(searchText);
    }

    // 게시글 상세 조회
    public CommunityPostRes getPostById(int postId) {
        return communityMapper.findById(postId);
    }

    // 게시글 수정
    public void updatePost(int postId, CommunityPostReq req) {
        communityMapper.modify(
                postId,
                req.getTitle(),
                req.getContent(),
                LocalDateTime.now()
        );
    }

    public boolean deletePost(int postId, int memberNoLogin) {
        CommunityPostRes post = communityMapper.findPostById(postId);
        if (post != null && post.getMemberNoLogin() == memberNoLogin) {
            communityMapper.deleteById(postId);  // is_deleted = TRUE 처리
            return true;
        }
        return false;
    }

    // 페이징
    public CommunityListRes getPostsWithPaging(String searchText, int page, int size) {
        int offset = (page - 1) * size;
        List<CommunityPostRes> posts = communityMapper.findAllWithPaging(searchText, size, offset);
        int totalCount = communityMapper.countAll(searchText);

        return new CommunityListRes(posts, totalCount);
    }


}
