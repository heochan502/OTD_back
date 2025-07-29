package com.otd.onetoday_back.community.controller;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.util.HttpUtils;
import com.otd.onetoday_back.community.dto.CommunityListRes;
import com.otd.onetoday_back.community.dto.CommunityPostReq;
import com.otd.onetoday_back.community.dto.CommunityPostRes;
import com.otd.onetoday_back.community.service.CommunityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/community")
@Slf4j
public class CommunityController {

    private final CommunityService communityService;

    // 게시글 등록
    @PostMapping("/create")
    public ResponseEntity<?> createPost(HttpServletRequest req, @ModelAttribute CommunityPostReq reqDto) {
        Integer loginId = (Integer) HttpUtils.getSessionValue(req, AccountConstants.MEMBER_ID_NAME);
        log.info("💬 로그인 ID: {}", loginId);

        if (loginId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        reqDto.setMemberNoLogin(loginId);
        communityService.createPost(reqDto);
        return ResponseEntity.ok("등록 성공");
    }


    @GetMapping("/detail/{postId}")
    public ResponseEntity<CommunityPostRes> getPost(@PathVariable int postId, HttpServletRequest req) {
        Integer memberId = (Integer) HttpUtils.getSessionValue(req, AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) memberId = -1;
        return ResponseEntity.ok(communityService.getPostById(postId, memberId));
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable int postId, @ModelAttribute CommunityPostReq req) {
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
    public ResponseEntity<?> toggleLike(
            @PathVariable int postId,
            HttpServletRequest request
    ) {
        Integer memberId = (Integer) HttpUtils.getSessionValue(request, AccountConstants.MEMBER_ID_NAME);
        if (memberId == null) {
            return ResponseEntity.status(401).body("로그인 필요");
        }

        communityService.toggleLike(postId, memberId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/list")
    public ResponseEntity<CommunityListRes> getList(
            @RequestParam(defaultValue = "") String searchText,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        CommunityListRes res = communityService.getPostsWithPaging(searchText, page, size);
        return ResponseEntity.ok(res);
    }




}
