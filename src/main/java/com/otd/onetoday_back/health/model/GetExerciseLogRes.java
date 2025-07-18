package com.otd.onetoday_back.health.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetExerciseLogRes {
    private int exerciseLogId;
    private int exerciseId;
    private int exerciseDuration;
    private String exerciseName;
    private String exerciseDatetime;
}
