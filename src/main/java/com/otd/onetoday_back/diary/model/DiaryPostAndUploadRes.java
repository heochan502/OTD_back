package com.otd.onetoday_back.diary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class DiaryPostAndUploadRes {
    private int id;
    private String diaryName;
    private String diaryContent;
    private String createdAt;
    private String mood;
    private String imageFileName;
    private List<UploadResponse> uploadResults;

    public DiaryPostAndUploadRes(int id, List<UploadResponse> uploadResults) {
        this.id = id;
        this.uploadResults = uploadResults;
    }
}