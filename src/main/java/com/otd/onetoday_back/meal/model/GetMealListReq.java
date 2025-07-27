package com.otd.onetoday_back.meal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;



@Getter
@Setter
@ToString
public class GetMealListReq {

    private int memberNoLogin;
    private String mealBrLuDi;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate  mealDay;

}
