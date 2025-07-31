package com.otd.onetoday_back.diary.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class DiaryPostReq {
    private int diaryId;
    private int memberNoLogin;
    private String diaryName;
    private String diaryContent;
    private String diaryImage;
    private List<MultipartFile> diaryImageFiles;

    @ToString.Exclude
    private String mood;
}