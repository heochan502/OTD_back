package com.otd.onetoday_back.community.domain;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommunityLike {
    private Long id;
    private int postId;
    private int memberId;
    private LocalDateTime createdAt;
}

