package com.otd.onetoday_back.community.controller;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.util.HttpUtils;
import com.otd.onetoday_back.community.dto.CommunityListRes;
import com.otd.onetoday_back.community.dto.CommunityPostReq;
import com.otd.onetoday_back.community.dto.CommunityPostRes;
import com.otd.onetoday_back.community.dto.PostFileDto;
import com.otd.onetoday_back.community.service.CommunityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/community")
@Slf4j
public class CommunityController {

    private final CommunityService communityService;

    // 게시글 등록 (파일 포함)
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost(HttpServletRequest req,
                                        @ModelAttribute CommunityPostReq reqDto) {
        try {
            Integer loginId = (Integer) HttpUtils.getSessionValue(req, AccountConstants.MEMBER_ID_NAME);
            log.info("💬 로그인 ID: {}", loginId);

            if (loginId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }

            reqDto.setMemberNoLogin(loginId);
            log.debug("📤 createPost title={}, files={}",
                    reqDto.getTitle(),
                    (reqDto.getFiles() == null ? 0 : reqDto.getFiles().length));

            communityService.createPost(reqDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("등록 성공");
        } catch (Exception e) {
            log.error("게시글 등록 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("게시글 등록 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/detail/{postId}")
    public ResponseEntity<CommunityPostRes> getPost(@PathVariable int postId, HttpServletRequest req) {
        Integer memberId = (Integer) HttpUtils.getSessionValue(req, AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) memberId = -1;
        return ResponseEntity.ok(communityService.getPostById(postId, memberId));
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable int postId,
                                           @ModelAttribute CommunityPostReq req) {
        communityService.updatePost(postId, req);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(HttpServletRequest req, @PathVariable int postId) {
        Integer loginId = (Integer) HttpUtils.getSessionValue(req, AccountConstants.MEMBER_ID_NAME);
        log.info("삭제 시도: postId={}, loginId={}", postId, loginId);

        if (loginId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        boolean result = communityService.deletePost(postId, loginId);
        if (result) {
            return ResponseEntity.ok("게시글 삭제 성공");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한 없음");
        }
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<?> toggleLike(@PathVariable int postId,
                                        HttpServletRequest request) {
        Integer memberId = (Integer) HttpUtils.getSessionValue(request, AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }
        communityService.toggleLike(postId, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<CommunityListRes> getList(@RequestParam(defaultValue = "") String searchText,
                                                    @RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        CommunityListRes res = communityService.getPostsWithPaging(searchText, page, size);
        return ResponseEntity.ok(res);
    }

    // 게시물 한 개의 첨부 이미지 목록 (DTO로 반환)
    @GetMapping("/files/{postId}")
    public ResponseEntity<List<PostFileDto>> getPostFiles(@PathVariable int postId) {
        List<PostFileDto> files = communityService.getPostFilesDto(postId);
        return ResponseEntity.ok(files);
    }

    // 첨부 이미지 추가 업로드 (DTO로 반환)
    @PostMapping(value = "/files/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addFiles(@PathVariable int postId,
                                      @RequestParam("files") MultipartFile[] files,
                                      HttpServletRequest req) {
        Integer loginId = (Integer) HttpUtils.getSessionValue(req, AccountConstants.MEMBER_ID_NAME);
        if (loginId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        if (files == null || files.length == 0) {
            return ResponseEntity.badRequest().body("업로드할 파일이 없습니다.");
        }
        try {
            List<PostFileDto> saved = communityService.addPostFilesDto(postId, loginId, files);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            log.error("파일 추가 업로드 실패: postId={}, memberNo={}", postId, loginId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패");
        }
    }

    // 첨부 이미지 삭제
    @DeleteMapping("/file/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable int fileId, HttpServletRequest req) {
        Integer loginId = (Integer) HttpUtils.getSessionValue(req, AccountConstants.MEMBER_ID_NAME);
        if (loginId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        try {
            communityService.deletePostFile(fileId, loginId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("파일 삭제 실패: fileId={}, memberNo={}", fileId, loginId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 실패");
        }
    }
}
