package com.otd.onetoday_back.health.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ExerciseLogCalendarGetReq {
    private String start;
    private String end;

    public ExerciseLogCalendarGetReq(String start, String end) {
        this.start = start;
        this.end = end;
    }
}
