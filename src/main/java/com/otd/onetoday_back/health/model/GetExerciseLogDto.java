package com.otd.onetoday_back.health.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GetExerciseLogDto {
    private int memberId;
    private String startDate;
    private String endDate;
}
