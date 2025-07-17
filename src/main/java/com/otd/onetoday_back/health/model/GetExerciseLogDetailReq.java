package com.otd.onetoday_back.health.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GetExerciseLogDetailReq {
    private int exerciseLogId;
    private int memberNo;
}
