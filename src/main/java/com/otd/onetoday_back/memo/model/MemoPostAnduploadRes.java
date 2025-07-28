package com.otd.onetoday_back.memo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemoPostAnduploadRes {
    private int memoId;
    private List<UploadResponse> uploadedImages;
}