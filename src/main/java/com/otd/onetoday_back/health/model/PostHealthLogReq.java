package com.otd.onetoday_back.health.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PostHealthLogReq {
    private int healthlogId;
    private int weight;
    private int height;
    private int systolicBp;
    private int diastolicBp;
    private int sugarLevel;
    private int moodLevel;
    private int sleepQuality;
    private String healthlogDatetime;
}
