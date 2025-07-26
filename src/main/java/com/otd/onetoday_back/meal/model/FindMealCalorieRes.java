package com.otd.onetoday_back.meal.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindMealCalorieRes {
    private int foodDbId;
    private String foodName;
    private int calorie;
}
