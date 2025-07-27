package com.otd.onetoday_back.meal.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class GetFoodInfoAllRes {
    private int calorie;
    private float protein;
    private float fat;
    private float carbohydrate;
    private float sugar;
    private float natrium;
}
