package com.otd.onetoday_back.meal.util;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FoodDBExcelDto {
    private int foodDBId;
    private String foodName;
    private String foodCategory;
    private int calorie;
    private float  protein;
    private float fat;
    private float carbohydrate;
    private float sugar;
    private float natrium;

}
