package com.otd.onetoday_back.diary.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class DiaryPutReq {
    private int diaryId;
    private int memberNoLogin;
    private String diaryName;
    private String diaryContent;
    private String diaryImage;
    private List<MultipartFile> diaryImageFiles;


    @ToString.Exclude
    private String mood;
}