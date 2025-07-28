package com.otd.onetoday_back.memo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoPutReq {
    private int id; // 수정할 메모의 ID
    private String memoName;
    private String memoContent;
    private String memoImage; // 이미지 파일명 (기존 이미지 유지하거나 새로 설정)
}