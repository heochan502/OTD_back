package com.otd.onetoday_back.meal.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class findFoodDetailInfoReq {
    private String mealBrLuDi;
    private int totalCalorie;
    private List<Integer> amount;
    private List<Integer> foodDbId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate mealDay;
}
