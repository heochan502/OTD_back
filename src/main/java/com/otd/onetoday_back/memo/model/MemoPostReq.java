package com.otd.onetoday_back.memo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class MemoPostReq {
    private int id; // 저장된 후 반환될 ID
    private int memberNoLogin;
    private String memoName;
    private String memoContent;
    private String memoImage; // 대표 이미지 파일명
    private List<MultipartFile> memoImageFiles;
}