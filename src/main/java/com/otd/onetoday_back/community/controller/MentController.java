package com.otd.onetoday_back.community.controller;

import com.otd.onetoday_back.community.dto.MentRes;
import com.otd.onetoday_back.community.service.MentService;
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

    @GetMapping("/{postId}")
    public ResponseEntity<List<MentRes>> getMentsByPostId(@PathVariable int postId) {
        List<MentRes> mentList = mentService.getMentsByPostId(postId);
        log.info("메모메모메모", mentList);
        return ResponseEntity.ok(mentList);
    }
}
