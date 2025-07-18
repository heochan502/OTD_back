package com.otd.onetoday_back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MemoPostAnduploadRes {
    private Integer id;
    private UploadResponse uploadResponse;
}
