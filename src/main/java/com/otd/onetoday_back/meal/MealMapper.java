package com.otd.onetoday_back.meal;


import com.otd.onetoday_back.meal.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    List<GetMealListRes> getDataByMemberNoId(GetMealListReq getData);
    //수정 밀카테고리
    int modifyByMealDayAndMealBrLuDi ( @Param("mealInfo") InputMealCategoryReq mealInfo,
                                       @Param("foodDbId") int foodDbId,
                                       @Param("foodAmount") float foodAmount);
    int modifyByMealTotalAndMealBrLuDi (InpuMealDetailDto mealInfo);
    int deleteMealCategory (InputMealCategoryReq mealInfo);

    int deleteMealTotal ( @Param("memberNoLogin") int memberNoLogin, @Param("mealInfo")findFoodDetailInfoReq mealInfo);

    //데이터가 지우는거
    int deleteMealCategoryIn ( @Param("memberNoLogin") int memberNoLogin, @Param("mealInfo")findFoodDetailInfoReq mealInfo);


}
