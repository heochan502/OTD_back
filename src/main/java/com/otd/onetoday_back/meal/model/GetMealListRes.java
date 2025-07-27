package com.otd.onetoday_back.meal.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class GetMealListRes {

    private String foodName ;
    private Date mealTime ;
    private int foodDbId;
    private int allDayCalorie;
    private int amount;
    private String mealBrLuDi;
    private int calorie;
    private float totalFat; //지방
    private float totalCarbohydrate; // 탄수화물
    private float totalProtein; // 단백질
}
