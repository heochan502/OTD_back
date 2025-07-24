package com.otd.onetoday_back.meal;


import com.otd.onetoday_back.meal.model.findFoodNameReq;
import com.otd.onetoday_back.meal.model.findFoodNameRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Controller;

import java.util.List;


@Mapper

public interface MealMapper {

    List<findFoodNameRes> findFoodNameForFoodName(String foodName);
    // 객체 재사용이라 이름이 같음  list안에 이름
    List<findFoodNameRes> findFoodCategory(String foodCategory);

    List<findFoodNameRes> findFoodNameForFoodNameAndCategory(findFoodNameReq foodName);

}
