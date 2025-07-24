package com.otd.onetoday_back.community.domain;

// 비즈니스 도메인 객체(Entity) 를 담는 공간
// 데이터베이스 테이블과 1:1로 매핑되는 핵심 데이터 구조
// community_post 테이블과 직접 연결되는 DB 엔티티 역할을 함

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Community {
    private int postId;            // 게시글 ID
    private int memberNoLogin;    // 작성자 회원번호
    private String title;         // 제목
    private String content;       // 내용

    // 파일 업로드용
    private String fileName;      // 실제 파일명
    private String filePath;      // 저장된 경로
    private String fileType;      // MIME 타입
    private LocalDateTime uploadedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int viewCount;
    private int like;
}
