package com.otd.onetoday_back.community.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentReq {
    private Long commentId;     // useGeneratedKeys로 세팅 가능해짐
    private int postId;
    private int memberNoLogin;
    private String content;
}

