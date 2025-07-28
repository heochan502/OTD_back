package com.otd.onetoday_back.meal.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Setter
@Builder
public class FoodIdAndAmountDto {
    private Integer foodDbId;
    private Integer  foodAmount;

}
