package com.otd.onetoday_back.community.controller;

import com.otd.onetoday_back.common.util.HttpUtils;
import com.otd.onetoday_back.community.dto.MentReq;
import com.otd.onetoday_back.community.dto.MentRes;
import com.otd.onetoday_back.community.service.MentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/comment")
public class MentController {

    private final MentService mentService;

    @PostMapping("/write")
    public ResponseEntity<?> writeComment(@RequestBody MentReq req) {
        mentService.write(req);
        return ResponseEntity.ok("댓글 등록 완료");
    }

    @GetMapping("/list/{postId}")
    public ResponseEntity<List<MentRes>> getComments(@PathVariable int postId) {
        return ResponseEntity.ok(mentService.getComments(postId));
    }

    @DeleteMapping("/comment/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId) {
        mentService.delete(commentId);
        return ResponseEntity.ok("댓글 삭제 완료");
    }
}
