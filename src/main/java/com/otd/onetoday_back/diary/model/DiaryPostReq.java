package com.otd.onetoday_back.diary.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class DiaryPostReq {
    private String diaryName;
    private String diaryContent;
    private Integer memberNoLogin;
    private String mood;
    private String imageFileName;
    private List<MultipartFile> diaryImageFiles;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}