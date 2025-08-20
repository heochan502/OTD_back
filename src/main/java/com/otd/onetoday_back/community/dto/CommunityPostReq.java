package com.otd.onetoday_back.community.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class CommunityPostReq {
    //회원정보 예를 들어 박인하를 이름에 넣엇으면 박인하가 들어오는 파일 req
    private int postId;
    private int memberNoLogin;
    private String title;
    private String content;
    private String filePath; // null로 처리
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int viewCount;
    private int like;
    private org.springframework.web.multipart.MultipartFile[] files;
}
