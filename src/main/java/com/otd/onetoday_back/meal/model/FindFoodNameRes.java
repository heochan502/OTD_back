package com.otd.onetoday_back.meal.model;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class FindFoodNameRes {
    private String foodName;
    private int calorie;
}
