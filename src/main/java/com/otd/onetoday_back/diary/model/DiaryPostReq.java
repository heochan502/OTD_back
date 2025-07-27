package com.otd.onetoday_back.diary.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class DiaryPostReq {
    private int id;
    private int memberNoLogin;
    private String diaryName;
    private String diaryContent;
    private String diaryImage; // 저장된 이미지 파일명 (1장)
    private List<MultipartFile> diaryImageFiles; // 업로드 시 사용할 MultipartFile 리스트
}