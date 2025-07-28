package com.otd.onetoday_back.memo.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MemoPutReq {
    private int id;
    private String memoName;
    private String memoContent;
    private String memoImage; // 덮어쓸 이미지 파일명
    private List<MultipartFile> memoImageFiles; // 수정 시 새 파일 업로드용 (선택)
}