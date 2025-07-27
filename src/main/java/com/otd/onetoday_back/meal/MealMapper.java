package com.otd.onetoday_back.meal;


import com.otd.onetoday_back.meal.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper

public interface MealMapper {

//    List<FindFoodNameRes> findFoodNameForFoodName(String foodName);
    // 객체 재사용이라 이름이 같음  list안에 이름
    List<FindFoodCategoryRes> findFoodCategory(String foodCategory);
    List<FindFoodNameRes> findFoodNameForFoodNameAndCategory(FindFoodNameReq foodName);
    int inputDayMealData ( InputMealCategoryReq mealInfo);
    List<GetFoodInfoAllRes>  getDetailFoodInfo(List<Integer>foodDbId);

    int inputTotalCalorie (InpuMealDetailDto sumData);

}
