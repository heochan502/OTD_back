package com.otd.onetoday_back.community.controller;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.util.HttpUtils;
import com.otd.onetoday_back.community.dto.MentReq;
import com.otd.onetoday_back.community.dto.MentRes;
import com.otd.onetoday_back.community.service.MentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/community/comment")
public class MentController {

    private final MentService mentService;

    // 조회: GET /api/OTD/community/comment/{postId}
    @GetMapping("/{postId}")
    public ResponseEntity<List<MentRes>> getMentsByPostId(@PathVariable int postId) {
        if (postId <= 0) return ResponseEntity.badRequest().build();
        List<MentRes> list = mentService.getMentsByPostId(postId);
        log.info("[MENT][LIST] postId={}, size={}", postId, list.size());
        return ResponseEntity.ok(list);
    }

    // 등록: POST /api/OTD/community/comment/create
    @PostMapping("/create")
    public ResponseEntity<MentRes> create(HttpServletRequest req, @RequestBody MentReq body) {
        Integer loginId = (Integer) HttpUtils.getSessionValue(req, AccountConstants.MEMBER_ID_NAME);
        if (loginId == null) return ResponseEntity.status(401).build();

        String content = body.getContent() == null ? null : body.getContent().trim();
        if (body.getPostId() <= 0 || content == null || content.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // commentId는 DB가 생성 → null로 전달
        MentReq reqDto = MentReq.builder()
                .commentId(null)
                .postId(body.getPostId())
                .memberNoLogin(loginId)
                .content(content)
                .build();

        MentRes saved = mentService.create(reqDto);
        log.info("[MENT][CREATE] postId={}, member={}, commentId={}",
                reqDto.getPostId(), loginId, saved.getCommentId());
        return ResponseEntity.status(201).body(saved);
    }

    // 삭제: DELETE /api/OTD/community/comment/delete/{commentId}
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> delete(HttpServletRequest req, @PathVariable long commentId) {
        Integer loginId = (Integer) HttpUtils.getSessionValue(req, AccountConstants.MEMBER_ID_NAME);
        if (loginId == null) return ResponseEntity.status(401).build();
        if (commentId <= 0) return ResponseEntity.badRequest().build();

        boolean ok = mentService.delete(commentId, loginId);
        log.info("[MENT][DELETE] commentId={}, member={}, ok={}", commentId, loginId, ok);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.status(403).build();
    }
}
