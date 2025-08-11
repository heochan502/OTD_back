package com.otd.onetoday_back.community.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
