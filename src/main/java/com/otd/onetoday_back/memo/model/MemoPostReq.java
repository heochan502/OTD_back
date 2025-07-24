package com.otd.onetoday_back.memo.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoPostReq {
    private Integer id;
    private Integer memberNoLogin;
    private String title;
    private String content;
    private List<MultipartFile> memoImageFiles;

//    public List<MultipartFile> getMemoImageFiles() {
//        return memoImageFiles;
//    }
//    public void setMemoImageFiles(List<MultipartFile> memoImageFiles) {
//        this.memoImageFiles = memoImageFiles;
//    }
}
