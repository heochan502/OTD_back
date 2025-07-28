package com.otd.onetoday_back.memo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MemoPostAnduploadRes {
    private int id;
    private List<UploadResponse> uploadResponseList;
}