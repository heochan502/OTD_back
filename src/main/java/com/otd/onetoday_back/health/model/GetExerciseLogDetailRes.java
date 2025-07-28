package com.otd.onetoday_back.health.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter
@Builder
@ToString
public class GetExerciseLogDetailRes {
    private int exerciselogId;
    private int exerciseId;
    private int exerciseDuration;
    private int exerciseKcal;
    private int effortLevel;
    private String exerciseDatetime;
}
