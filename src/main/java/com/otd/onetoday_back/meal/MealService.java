package com.otd.onetoday_back.meal;


import com.otd.onetoday_back.meal.model.FindFoodNameReq;
import com.otd.onetoday_back.meal.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MealService {
    private final MealMapper mealMapper;

    List<FindFoodNameRes> findFoodName (FindFoodNameReq foodInfo) {
//        if (foodInfo.getFoodCategory() == null || foodInfo.getFoodCategory().isEmpty()) {
//         return   mealMapper.findFoodNameForFoodName(foodInfo.getFoodName());
//        }
//        else
//        {
            return   mealMapper.findFoodNameForFoodNameAndCategory(foodInfo);
//        }
    }

    List<FindFoodCategoryRes> findFoodCategory (FindFoodNameReq foodInfo) {
                 return   mealMapper.findFoodCategory(foodInfo.getFoodCategory());
    }
    FindMealCalorieRes findMealCalorie (FindFoodNameReq foodInfo) {
        return mealMapper.findMealCalorie(foodInfo);
    }


}
