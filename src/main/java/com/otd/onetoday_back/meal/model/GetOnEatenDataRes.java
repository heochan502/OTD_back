package com.otd.onetoday_back.meal.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetOnEatenDataRes {
    private int totalCalorie;
    private float totalProtein;
    private float totalFat;
    private float totalCarbohydrate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate mealDay;

}
