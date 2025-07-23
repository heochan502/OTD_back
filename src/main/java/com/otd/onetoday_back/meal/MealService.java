package com.otd.onetoday_back.meal;


import com.otd.onetoday_back.meal.model.findFoodNameReq;
import com.otd.onetoday_back.meal.model.findFoodNameRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MealService {
    private final MealMapper mealMapper;

    List<findFoodNameRes> findFoodName (findFoodNameReq foodInfo) {
        if (foodInfo.getFoodName() == null || foodInfo.getFoodName().isEmpty()) {
            return   mealMapper.findFoodCategory(foodInfo.getFoodCategory());
        }
        else if (foodInfo.getFoodCategory() == null || foodInfo.getFoodCategory().isEmpty()) {
         return   mealMapper.findFoodNameForFoodName(foodInfo.getFoodName());
        }
        else
        {
            return   mealMapper.findFoodNameForFoodNameAndCategory(foodInfo);
        }

    }

}
