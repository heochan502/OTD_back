package com.otd.onetoday_back.community.service;

import com.otd.onetoday_back.community.common.file.FileStorageService;
import com.otd.onetoday_back.community.domain.CommunityLike;
import com.otd.onetoday_back.community.domain.CommunityPostFile;
import com.otd.onetoday_back.community.dto.CommunityListRes;
import com.otd.onetoday_back.community.dto.CommunityPostReq;
import com.otd.onetoday_back.community.dto.CommunityPostRes;
import com.otd.onetoday_back.community.mapper.CommunityLikeMapper;
import com.otd.onetoday_back.community.mapper.CommunityMapper;
import com.otd.onetoday_back.community.mapper.CommunityPostFileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityLikeMapper likeMapper;
    private final CommunityMapper communityMapper;
    private final CommunityPostFileMapper postFileMapper;   // ⬅️ 첨부파일 전담 매퍼
    private final FileStorageService fileStorageService;

    // 게시글 등록 (파일 포함)
    @Transactional
    public void createPost(CommunityPostReq req) {
        req.setCreatedAt(LocalDateTime.now());
        req.setUpdatedAt(LocalDateTime.now());
        req.setViewCount(0);
        req.setLike(0);
        req.setFilePath(null); // 대표이미지는 첫 파일 처리 후 UPDATE

        // 1) 게시글 INSERT (postId 세팅)
        communityMapper.save(req);
        final int postId = req.getPostId();
        log.info("createPost: inserted postId={}", postId);

        // 2) 파일 업로드 처리
        String firstFilePath = null;
        MultipartFile[] files = req.getFiles(); // DTO: MultipartFile[] files
        log.info("createPost: upload files count={}", (files == null ? 0 : files.length));

        if (files != null) {
            for (MultipartFile mf : files) {
                if (mf == null || mf.isEmpty()) continue;
                try {
                    // 2-1) 디스크 저장 → 정적 접근 경로 리턴
                    String urlPath = fileStorageService.savePostFile(postId, req.getMemberNoLogin(), mf);

                    // 2-2) 파일 메타 DB 저장 (community_postfile)
                    CommunityPostFile meta = CommunityPostFile.builder()
                            .postId(postId)
                            .memberNoLogin(req.getMemberNoLogin())
                            .fileName(mf.getOriginalFilename())
                            .filePath(urlPath)
                            .fileType(fileStorageService.detectContentType(mf))
                            .uploadedAt(LocalDateTime.now())
                            .build();

                    postFileMapper.insert(meta); // ⬅️ 전담 매퍼 사용

                    if (firstFilePath == null) firstFilePath = urlPath;
                } catch (IOException e) {
                    log.error("파일 저장 실패: postId={}, file={}", postId, mf.getOriginalFilename(), e);
                    throw new RuntimeException("파일 저장 중 오류 발생", e);
                } catch (Exception e) {
                    log.error("파일 메타 저장 실패(community_postfile): postId={}, file={}", postId, mf.getOriginalFilename(), e);
                    throw e;
                }
            }
        }

        // 3) 대표 이미지(첫 파일)를 community_post.file_path에 반영
        if (firstFilePath != null) {
            communityMapper.updatePostFilePath(postId, firstFilePath);
        }
    }

    // 좋아요 토글 및 반영
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
        res.setLikedByCurrentUser(like != null);
        return res;
    }

    // 게시글 목록 조회
    public List<CommunityPostRes> getAllPosts(String searchText) {
        return communityMapper.findAll(searchText);
    }

    // 게시글 상세 조회 (오버로드)
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

    // 게시글 삭제(논리)
    public boolean deletePost(int postId, int memberNoLogin) {
        CommunityPostRes post = communityMapper.findPostById(postId);
        if (post != null && post.getMemberNoLogin() == memberNoLogin) {
            communityMapper.deleteById(postId);  // is_deleted = TRUE
            return true;
        }
        return false;
    }

    // 페이징 조회
    public CommunityListRes getPostsWithPaging(String searchText, int page, int size) {
        int offset = (page - 1) * size;
        List<CommunityPostRes> posts = communityMapper.findAllWithPaging(searchText, size, offset);
        int totalCount = communityMapper.countAll(searchText);
        return new CommunityListRes(posts, totalCount);
    }
    //게시물 한개에 여러개 사진 보여주기
    @Transactional(readOnly = true)
    public List<CommunityPostFile> getPostImages(int postId) {
        return communityMapper.findFilesByPostId(postId);
    }

}
