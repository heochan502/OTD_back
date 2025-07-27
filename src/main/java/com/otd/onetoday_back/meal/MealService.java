package com.otd.onetoday_back.meal;


import com.otd.onetoday_back.meal.model.FindFoodNameReq;
import com.otd.onetoday_back.meal.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    int inputDayMealData (Integer memberNoLogin , findFoodDetailInfoReq mealInfo) {


        if(memberNoLogin ==null)
        {
            return 0;
        }


        if (mealInfo.getFoodDbId() != null && !mealInfo.getFoodDbId().isEmpty()){
        InputMealCategoryReq inputMealData = InputMealCategoryReq.builder()
                .memberNoLogin(memberNoLogin)
                .foodDbId(mealInfo.getFoodDbId())
                .mealDay(mealInfo.getMealDay())
                .build();
        log.info("foodInfo: {}", inputMealData);

            List<GetFoodInfoAllRes> result = mealMapper.getDetailFoodInfo(mealInfo.getFoodDbId());

//        InpuMealDetailDto resultTotalData = new InpuMealDetailDto();

            InpuMealDetailDto sumData = new InpuMealDetailDto();
            sumData.setMealDay(mealInfo.getMealDay());
            sumData.setMemberNoLogin(memberNoLogin);
            sumData.setMealBrLuDi(mealInfo.getMealBrLuDi());

            for (int i = 0; i < result.size(); i++) {
                float amount = mealInfo.getAmount().get(i);
                GetFoodInfoAllRes food = result.get(i);

                sumData.setTotalCalorie((int)(sumData.getTotalCalorie() + (food.getCalorie() / 100) * amount));
                sumData.setTotalProtein((float)(sumData.getTotalProtein() + (food.getProtein() / 100.0) * amount));
                sumData.setTotalFat((float)(sumData.getTotalFat() + (food.getFat() / 100.0) * amount));
                sumData.setTotalCarbohydrate((float)(sumData.getTotalCarbohydrate() + (food.getCarbohydrate() / 100.0) * amount));
                sumData.setTotalSugar((float)(sumData.getTotalSugar() + (food.getSugar() / 100.0) * amount));
                sumData.setTotalNatrium((float)(sumData.getTotalNatrium() + (food.getNatrium() / 100.0) * amount));
            }

            int res = mealMapper.inputTotalCalorie(sumData);
            log.info("토탈 데이터 성공 실패 : {}", res);

            return mealMapper.inputDayMealData( inputMealData);
        }




        return 0;
    }


}
