package com.otd.onetoday_back.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/otd/community")
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping
    public ResponseEntity<Void> createPost(@ModelAttribute communityPostReq req)
}
