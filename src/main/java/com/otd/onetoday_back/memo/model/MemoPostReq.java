package com.otd.onetoday_back.memo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MemoPostReq {
    private Integer memberNoLogin;
    private int memoId;
    private String memoName;
    private String memoContent;
    private String memoImage;
    private List<MultipartFile> memoImageFiles;
}