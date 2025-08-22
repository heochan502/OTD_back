package com.otd.onetoday_back.community.service;

import com.otd.onetoday_back.community.common.file.FileStorageService;
import com.otd.onetoday_back.community.domain.CommunityLike;
import com.otd.onetoday_back.community.domain.CommunityPostFile;
import com.otd.onetoday_back.community.dto.CommunityListRes;
import com.otd.onetoday_back.community.dto.CommunityPostReq;
import com.otd.onetoday_back.community.dto.CommunityPostRes;
import com.otd.onetoday_back.community.dto.PostFileDto;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityLikeMapper likeMapper;
    private final CommunityMapper communityMapper;
    private final CommunityPostFileMapper postFileMapper;   // 첨부파일 전담 매퍼
    private final FileStorageService fileStorageService;

    /* =========================
       게시글 등록 (파일 포함)
       ========================= */
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
        MultipartFile[] files = req.getFiles();
        log.info("createPost: upload files count={}", (files == null ? 0 : files.length));

        if (files != null) {
            for (MultipartFile mf : files) {
                if (mf == null || mf.isEmpty()) continue;
                try {
                    // 2-1) 디스크 저장 → 정적 접근 경로 리턴
                    String urlPath = fileStorageService.savePostFile(postId, req.getMemberNoLogin(), mf);

                    // 2-2) 파일 메타 DB 저장
                    CommunityPostFile meta = CommunityPostFile.builder()
                            .postId(postId)
                            .memberNoLogin(req.getMemberNoLogin())
                            .fileName(mf.getOriginalFilename())
                            .filePath(urlPath)
                            .fileType(fileStorageService.detectContentType(mf))
                            .uploadedAt(LocalDateTime.now())
                            .build();

                    postFileMapper.insert(meta); // useGeneratedKeys로 PK 세팅되는지 확인

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

    /* =========================
       게시글에 첨부파일 추가 (엔티티 반환 - 기존)
       ========================= */
    @Transactional
    public List<CommunityPostFile> addPostFiles(int postId, int memberNoLogin, MultipartFile[] files) {
        if (files == null || files.length == 0) return List.of();

        // 권한 체크
        CommunityPostRes post = communityMapper.findPostById(postId);
        if (post == null) throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
        if (post.getMemberNoLogin() != memberNoLogin) throw new SecurityException("첨부파일 추가 권한이 없습니다.");

        List<CommunityPostFile> existing = postFileMapper.findByPostId(postId);
        boolean hadNoFiles = (existing == null || existing.isEmpty());

        List<CommunityPostFile> saved = new ArrayList<>();
        String firstNewPath = null;

        for (MultipartFile mf : files) {
            if (mf == null || mf.isEmpty()) continue;
            try {
                String urlPath = fileStorageService.savePostFile(postId, memberNoLogin, mf);

                CommunityPostFile meta = CommunityPostFile.builder()
                        .postId(postId)
                        .memberNoLogin(memberNoLogin)
                        .fileName(mf.getOriginalFilename())
                        .filePath(urlPath)
                        .fileType(fileStorageService.detectContentType(mf))
                        .uploadedAt(LocalDateTime.now())
                        .build();

                postFileMapper.insert(meta);
                saved.add(meta);

                if (firstNewPath == null) firstNewPath = urlPath;
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 중 오류 발생", e);
            }
        }

        // 기존 첨부가 없던 글이면 대표 이미지 세팅
        if (hadNoFiles && firstNewPath != null) {
            communityMapper.updatePostFilePath(postId, firstNewPath);
        }

        return saved;
    }

    /* =========================
       게시글에 첨부파일 추가 (DTO 반환 - 컨트롤러에서 사용)
       ========================= */
    @Transactional
    public List<PostFileDto> addPostFilesDto(int postId, int memberNoLogin, MultipartFile[] files) {
        List<CommunityPostFile> saved = addPostFiles(postId, memberNoLogin, files);
        return saved.stream().map(this::toDto).toList();
    }

    /* =========================
       첨부파일 삭제
       ========================= */
    @Transactional
    public void deletePostFile(int fileId, int memberNoLogin) {
        CommunityPostFile file = postFileMapper.findById(fileId);
        if (file == null) throw new IllegalArgumentException("첨부파일이 존재하지 않습니다.");

        CommunityPostRes post = communityMapper.findPostById(file.getPostId());
        if (post == null) throw new IllegalStateException("원본 게시글이 존재하지 않습니다.");
        if (post.getMemberNoLogin() != memberNoLogin) throw new SecurityException("첨부파일 삭제 권한이 없습니다.");

        // DB 삭제
        postFileMapper.deleteById(fileId);

        // 대표 이미지였다면 대표 갱신
        if (post.getFilePath() != null && post.getFilePath().equals(file.getFilePath())) {
            List<CommunityPostFile> remain = postFileMapper.findByPostId(post.getPostId());
            String newRep = (remain != null && !remain.isEmpty()) ? remain.get(0).getFilePath() : null;
            communityMapper.updatePostFilePath(post.getPostId(), newRep);
        }

        // 물리 파일 삭제를 사용할 경우:
        // fileStorageService.deletePhysicalFile(file.getFilePath());
    }

    /* =========================
       좋아요 토글 및 반영
       ========================= */
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

    /* =========================
       첨부 이미지 목록 (엔티티)
       ========================= */
    @Transactional(readOnly = true)
    public List<CommunityPostFile> getPostImages(int postId) {
        // 기존 communityMapper.findFilesByPostId(postId) 대신 파일 전담 매퍼 사용 권장
        return postFileMapper.findByPostId(postId);
    }

    /* =========================
       첨부 이미지 목록 (DTO) - 컨트롤러에서 사용
       ========================= */
    @Transactional(readOnly = true)
    public List<PostFileDto> getPostFilesDto(int postId) {
        List<CommunityPostFile> entities = postFileMapper.findByPostId(postId);
        return entities.stream().map(this::toDto).toList();
    }

    /* =========================
       매핑 유틸
       ========================= */
    private PostFileDto toDto(CommunityPostFile f) {
        Integer id = extractFileId(f);
        return PostFileDto.builder()
                .fileId(id)
                .filePath(f.getFilePath())
                .fileName(f.getFileName()) // 컬럼/게터명이 originalName이면 변경
                .build();
    }

    /**
     * 엔티티의 PK 필드명이 구현마다 다를 수 있어 안전하게 추출
     * - getFileId(), getPostFileId() 둘 다 시도
     */
    private Integer extractFileId(CommunityPostFile f) {
        try {
            // 가장 흔한 이름
            var m1 = CommunityPostFile.class.getMethod("getFileId");
            Object v1 = m1.invoke(f);
            if (v1 instanceof Number) return ((Number) v1).intValue();
        } catch (Exception ignore) {}

        try {
            // 대안: postFileId
            var m2 = CommunityPostFile.class.getMethod("getPostFileId");
            Object v2 = m2.invoke(f);
            if (v2 instanceof Number) return ((Number) v2).intValue();
        } catch (Exception ignore) {}

        // 마지막 대안: 매퍼에서 useGeneratedKeys로 insert 후 id 필드 세팅 확인 필요
        log.warn("CommunityPostFile PK를 찾지 못했습니다. 엔티티 PK 게터명을 확인하세요. entity={}", f);
        return null;
    }
}
