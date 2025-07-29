package com.otd.onetoday_back.memo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class MemoPutReq {
    private int id; // 수정할 메모의 ID
    private int memberNoLogin;
    private String memoName;
    private String memoContent;
    private String memoImageFileName; // 이미지 파일명 (기존 이미지 유지하거나 새로 설정)
    private List<MultipartFile> memoImageFiles;
}