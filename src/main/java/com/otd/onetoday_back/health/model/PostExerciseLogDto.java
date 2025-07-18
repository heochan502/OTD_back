package com.otd.onetoday_back.health.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PostExerciseLogDto {
    private int exerciseId;
    private int exerciseKcal;
    private String exerciseDatetime;
    private int exerciseDuration;
    private int effortLevel;
    private int memberNo;
}
