package com.otd.onetoday_back.memo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MemoPostReq {
    private int id;
    private int memberNoLogin;
    private String memoName;
    private String memoContent;
    private String memoImageFileName;
    private List<MultipartFile> memoImageFiles;
}