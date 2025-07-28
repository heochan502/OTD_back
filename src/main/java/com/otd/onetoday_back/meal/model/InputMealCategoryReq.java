package com.otd.onetoday_back.meal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@ToString
@Setter
@Builder
public class InputMealCategoryReq {
    private int memberNoLogin;
    @Singular("foodDbId")
    private List<FoodIdAndAmountDto> mealDetails;
    private String mealBrLuDi;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate mealDay;
}
