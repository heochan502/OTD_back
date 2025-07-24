package com.otd.onetoday_back.health.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GetExerciseRes {
    private int exerciseId;
    private String exerciseName;
    private float met;
}
