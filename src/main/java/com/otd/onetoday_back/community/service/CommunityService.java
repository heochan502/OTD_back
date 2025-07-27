package com.otd.onetoday_back.community.service;

import com.otd.onetoday_back.community.dto.CommunityPostReq;
import com.otd.onetoday_back.community.dto.CommunityPostRes;
import com.otd.onetoday_back.community.mapper.CommunityLikeMapper;
import com.otd.onetoday_back.community.mapper.CommunityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
        String filePath = null;

        communityMapper.save(
                req.getMemberNoLogin(),
                req.getTitle(),
                req.getContent(),
                filePath,
                LocalDateTime.now(),
                LocalDateTime.now(),
                0, // viewCount 초기값
                0  // like 초기값
        );
        // 3. 파일 저장 (Optional)
        MultipartFile file = req.getFile();
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String savePath = "/your/upload/path/" + fileName;
                file.transferTo(new File(savePath));
                filePath = "/uploads/" + fileName;

                // postId 가져오기 필요
                int postId = req.getPostId(); // 또는 save 후 selectKey로 받아오기
                communityMapper.saveFile(postId, filePath);
            } catch (IOException e) {
                throw new RuntimeException("파일 업로드 실패", e);
            }
        }


    }
    public void toggleLike(int postId, int memberId) {
        CommunityLike existing = likeMapper.findByPostIdAndMemberId(postId, memberId);

        if (existing != null) {
            likeMapper.deleteByPostIdAndMemberId(postId, memberId);
        } else {
            CommunityLike newLike = new CommunityLike();
            newLike.setPostId(postId);
            newLike.setMemberId(memberId);
            likeMapper.insert(newLike);
        }

        int updatedCount = likeMapper.countByPostId(postId);
        communityMapper.updateLikeCount(postId, updatedCount);
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

    // 게시글 삭제
    public void deletePost(int postId) {
        communityMapper.deleteById(postId);
    }
}
