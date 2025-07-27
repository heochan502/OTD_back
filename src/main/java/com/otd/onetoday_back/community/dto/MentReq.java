package com.otd.onetoday_back.community.dto;

import lombok.Data;

@Data
public class MentReq {
    private int postId;
    private int memberNoLogin;
    private String content;
}
