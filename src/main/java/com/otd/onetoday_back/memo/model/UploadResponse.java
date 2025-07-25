package com.otd.onetoday_back.memo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponse {
    private String fileName;
    private String originalName;
    private String message; // UploadResponse(String, String, String) 형식
}
