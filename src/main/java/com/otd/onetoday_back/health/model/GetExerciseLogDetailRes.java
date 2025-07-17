package com.otd.onetoday_back.health.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GetExerciseLogDetailRes {
    private int exerciseLogId;
    private int exerciseId;
    private String exerciseName;
    private int memberNo;
    private int exerciseDuration;
    private int exerciseKcal;
    private int effortLevel;
}
