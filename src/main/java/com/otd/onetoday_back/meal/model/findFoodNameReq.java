package com.otd.onetoday_back.meal.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class findFoodNameReq {
    private String foodName;
    private String foodCategory;
}
