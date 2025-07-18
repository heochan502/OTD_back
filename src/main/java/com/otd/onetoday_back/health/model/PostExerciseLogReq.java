package com.otd.onetoday_back.health.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostExerciseLogReq {
    private int exerciseId;
    private int memberNo;
    private int exerciseKcal;
    private String exerciseDatetime;
    private int exerciseDuration;
    private int effortLevel;
}
