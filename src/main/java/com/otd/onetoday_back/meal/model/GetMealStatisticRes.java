package com.otd.onetoday_back.meal.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetMealStatisticRes {

    private int totalCalorie; //칼로리
    private float totalFat; //지방
    private float totalCarbohydrate; // 탄수화물
    private float totalProtein; // 단백질
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate mealDay;
}
