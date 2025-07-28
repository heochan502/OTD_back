package com.otd.onetoday_back.meal.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindFoodNameReq {
    private String foodName;
    private String foodCategory;
}
