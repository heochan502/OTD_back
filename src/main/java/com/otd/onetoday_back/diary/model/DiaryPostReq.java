package com.otd.onetoday_back.diary.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class DiaryPostReq {
    private String diaryName;
    private String diaryContent;
    private String diaryImage; // 저장된 이미지 파일명
    private List<MultipartFile> diaryImageFiles; // 업로드용
    private int memberNoLogin;
    private int id; // save 시 useGeneratedKeys="true" 에서 id 채워짐
}