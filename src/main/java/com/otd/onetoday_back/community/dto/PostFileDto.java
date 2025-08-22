package com.otd.onetoday_back.community.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PostFileDto {
    private Integer fileId;     // 삭제 PK (필수)
    private String filePath;    // URL or 상대경로
    private String fileName;    // 원본 파일명(선택)
}
