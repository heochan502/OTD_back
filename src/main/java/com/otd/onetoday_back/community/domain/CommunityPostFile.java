package com.otd.onetoday_back.community.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommunityPostFile {
    private int postId;
    private int memberNoLogin;
    private String fileName;
    private String filePath;   // /files/community/{postId}/{uuid}.ext
    private String fileType;   // MIME
    private LocalDateTime uploadedAt;
}
