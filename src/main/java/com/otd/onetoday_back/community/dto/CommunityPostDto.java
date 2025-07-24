package com.otd.onetoday_back.community.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommunityPostDto {
    private int postId;        // 게시글 번호 (PK, auto-increment)
    private int memberNoLogin;          // 로그인한 회원 번호
    private String title;           // 게시글 제목
    private String content;         // 게시글 내용
    private String filePath;    // 서버에 저장된 파일 경로
    private LocalDateTime createdAt;// 작성일
    private LocalDateTime updatedAt;// 수정일
    private int viewCount;          // 조회수
    private int like;               // 좋아요 수
}
