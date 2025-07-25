package com.otd.onetoday_back.memo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class MemoGetOneRes {
    private int id;
    private String title;
    private String content;
    private String createdAt;
    private String imageFileName;
}
