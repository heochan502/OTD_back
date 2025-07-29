package com.otd.onetoday_back.memo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MemoGetOneRes {
    private int id;
    private int memberNoLogin;
    private String memoName;
    private String memoContent;
    private String imageFileName;
    private LocalDateTime createdAt;
}
