package com.otd.onetoday_back.health.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GetHealthLogDetailReq {
    private int healthlogId;
    private int memberNo;
}
