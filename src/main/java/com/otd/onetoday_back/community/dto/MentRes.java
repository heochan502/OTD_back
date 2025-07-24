package com.otd.onetoday_back.community.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MentRes {
    private int commentId;
    private int postId;
    private int memberNoLogin;
    private String memberNick;
    private String memberImg;
    private String content;
    private LocalDateTime updatedAt;
}
