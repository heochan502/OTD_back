package com.otd.onetoday_back.health.model;

import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;


@Getter
@ToString
public class GetExerciseLogReq {

    private String startDate;
    private String endDate;

    @ConstructorProperties({"start_date","end_date"})
    public GetExerciseLogReq(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
