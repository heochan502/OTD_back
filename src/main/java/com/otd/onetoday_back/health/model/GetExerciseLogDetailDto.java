package com.otd.onetoday_back.health.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GetExerciseLogDetailDto {
    private int exerciselogId;
    private int exerciseId;
    private int exerciseDuration;
    private int exerciseKcal;
    private int effortLevel;
    private String exerciseDatetime;
    private int memberId;
}
