package com.otd.onetoday_back.community.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommunityGetRes {
    //회원정보 예를 들어 박인하가 조회하면 박인하 이름이 내정보에 뜨도록 데이터를 주는 파일 res
    private int postId;
    private int memberNoLogin;
    private String title;
    private String content;
    private String filePath;          // 클라이언트가 접근할 수 있는 파일 URL 또는 경로
    private LocalDateTime createdAt;
    private int viewCount;
    private int like;
}
