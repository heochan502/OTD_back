package com.otd.onetoday_back.community.domain;

import lombok.*;

import java.time.LocalDateTime;
// 비즈니스 도메인 객체(Entity) 를 담는 공간
// 데이터베이스 테이블과 1:1로 매핑되는 핵심 데이터 구조
// community_comment 테이블과 직접 연결되는 DB 엔티티 역할을 함

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ment {
    private int commentId;
    private int postId;
    private int memberNoLogin;
    private String content;        // 댓글 내용
    private LocalDateTime updatedAt;
}
