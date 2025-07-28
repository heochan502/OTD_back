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
    private String diaryImage;
    private List<MultipartFile> diaryImageFiles;
}