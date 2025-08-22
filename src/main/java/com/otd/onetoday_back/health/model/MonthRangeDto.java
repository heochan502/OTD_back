package com.otd.onetoday_back.health.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthRangeDto {
    private int memberId;
    private String start;
    private String end;
}
