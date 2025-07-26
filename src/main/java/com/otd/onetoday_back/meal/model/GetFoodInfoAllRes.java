package com.otd.onetoday_back.meal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetFoodInfoAllRes {
    private int foodDbId;
    private int calorieDbId;
    private float protein;
    private float fat;
    private float carbohydrate;
    private float sugar;
    private float natrium;

}
