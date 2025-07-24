package com.otd.onetoday_back.community.controller;

import com.otd.onetoday_back.community.dto.MentReq;
import com.otd.onetoday_back.community.dto.MentRes;
import com.otd.onetoday_back.community.service.MentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/community/ment")
public class MentController {

    private final MentService mentService;

    // 댓글 작성
    @PostMapping("/create")
    public ResponseEntity<Void> createMent(@RequestBody MentReq req) {
        mentService.saveMent(req);
        return ResponseEntity.ok().build();
    }

    // 게시글에 달린 댓글 조회
    @GetMapping("/list/{postId}")
    public ResponseEntity<List<MentRes>> getMentList(@PathVariable int postId) {
        return ResponseEntity.ok(mentService.getMentListByPostId(postId));
    }

    // 댓글 삭제
    @DeleteMapping("/delete/{mentId}")
    public ResponseEntity<Void> deleteMent(@PathVariable int mentId) {
        mentService.deleteMent(mentId);
        return ResponseEntity.ok().build();
    }
}
