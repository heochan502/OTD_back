package com.otd.onetoday_back.memo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MemoPostReq {
    private int id;
    private String memoName;
    private String memoContent;
    private int memberNoLogin;
    private String memoImage; // 저장할 대표 이미지 파일명
    private List<MultipartFile> memoImageFiles; // 프론트에서 업로드되는 이미지들
}