package com.otd.onetoday_back.diary.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class DiaryPutReq {
    private int id;
    private String diaryName;
    private String diaryContent;
    private String imageFileName;

    // multipart 요청으로 들어오는 이미지들 (nullable)
    private List<MultipartFile> diaryImageFiles;

    private int memberNoLogin; // 로그인한 회원 정보 (서버에서 주입)

    @ToString.Exclude
    private String mood;
}