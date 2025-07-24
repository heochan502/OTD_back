package com.otd.onetoday_back.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityPostRes {
    private int postId;
    private int memberNoLogin;
    private String memberNick;   // 작성자 닉네임
    private String memberImg;    // 작성자 프로필 이미지
    private String title;
    private String content;
    private String filePath;     // 대표 파일 (썸네일)
    private LocalDateTime createdAt;
    private int viewCount;
    private int like;
    private int commentCount;    // 댓글 수
    private boolean likedByCurrentUser;
}
