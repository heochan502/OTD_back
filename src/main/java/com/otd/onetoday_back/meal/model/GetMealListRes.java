package com.otd.onetoday_back.meal.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
public class GetMealListRes {

    private String foodName ;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate mealDay;
    private int foodDbId;
    private int allDayCalorie;
    private int amount;
    private String mealBrLuDi;
    private int calorie;
    private float totalFat; //지방
    private float totalCarbohydrate; // 탄수화물
    private float totalProtein; // 단백질
}
