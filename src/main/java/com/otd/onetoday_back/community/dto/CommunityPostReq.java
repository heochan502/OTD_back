package com.otd.onetoday_back.community.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommunityPostReq {
    //회원정보 예를 들어 박인하를 이름에 넣엇으면 박인하가 들어오는 파일 req
    private int postId;
    private int memberNoLogin;
    private String title;
    private String content;
    private MultipartFile file;  // 클라이언트가 업로드하는 파일
}
