package com.otd.onetoday_back.memo.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoPostReq {
    private String title;
    private String content;
    private MultipartFile memoImageFile;
}
