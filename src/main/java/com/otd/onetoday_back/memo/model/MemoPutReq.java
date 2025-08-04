package com.otd.onetoday_back.memo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class MemoPutReq {
    private int memoId;
    private int memberNoLogin;
    private String memoName;
    private String memoContent;
    private String memoImage;
    private List<MultipartFile> memoImageFiles;
}