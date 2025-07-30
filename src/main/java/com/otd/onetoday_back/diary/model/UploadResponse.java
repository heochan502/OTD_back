package com.otd.onetoday_back.diary.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResponse {
    private String fileName;
    private String originalName;
    private String message;
}