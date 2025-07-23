package com.otd.onetoday_back.memo.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoGetRes {
    private int id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
