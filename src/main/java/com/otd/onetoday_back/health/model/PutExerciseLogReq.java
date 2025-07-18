package com.otd.onetoday_back.health.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PutExerciseLogReq {
    private int exerciselogId;
    private int exerciseId;
    private String exerciseDatetime;
    private int exerciseKcal;
    private int exerciseDuration;
    private int effortLevel;
}
