package com.otd.onetoday_back.model;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Data
public class MemoPostReq {
    private String title;
    private String content;
    private MultipartFile memoImageFile;

}
