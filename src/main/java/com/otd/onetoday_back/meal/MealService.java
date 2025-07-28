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



    private InpuMealDetailDto  calculateTotal (findFoodDetailInfoReq mealInfo, int memberNoLogin ) {
        InpuMealDetailDto sumData = new InpuMealDetailDto();
        List<GetFoodInfoAllRes> result = mealMapper.getDetailFoodInfo(mealInfo.getFoodDbId());

        sumData = new InpuMealDetailDto(); // 필드에 할당
        sumData.setMealDay(mealInfo.getMealDay());
        sumData.setMemberNoLogin(memberNoLogin);
        sumData.setMealBrLuDi(mealInfo.getMealBrLuDi());

        for (int i = 0; i < result.size(); i++) {
            float amount = mealInfo.getAmount().get(i);
            GetFoodInfoAllRes food = result.get(i);

            sumData.setTotalCalorie((int)(sumData.getTotalCalorie() + (food.getCalorie() / 100.0) * amount));
            sumData.setTotalProtein(sumData.getTotalProtein() + (food.getProtein() / 100.0f) * amount);
            sumData.setTotalFat(sumData.getTotalFat() + (food.getFat() / 100.0f) * amount);
            sumData.setTotalCarbohydrate(sumData.getTotalCarbohydrate() + (food.getCarbohydrate() / 100.0f) * amount);
            sumData.setTotalSugar(sumData.getTotalSugar() + (food.getSugar() / 100.0f) * amount);
            sumData.setTotalNatrium(sumData.getTotalNatrium() + (food.getNatrium() / 100.0f) * amount);
        }

        // DB 저장
        return sumData;
    }
    private  InputMealCategoryReq indataMealCategory (Integer memberNoLogin , findFoodDetailInfoReq mealInfo)
    {

        List<FoodIdAndAmountDto> detail = new ArrayList<>();
        for (int  i=0; i < mealInfo.getFoodDbId().size(); i++)
        {
            detail.add( FoodIdAndAmountDto.builder()
                    .foodDbId(mealInfo.getFoodDbId().get(i))
                    .foodAmount(mealInfo.getAmount().get(i))
                    .build() );
        }
        InputMealCategoryReq inputMealData = InputMealCategoryReq.builder()
                .memberNoLogin(memberNoLogin)
                .mealDay(mealInfo.getMealDay())
                .mealDetails(detail)
                .mealBrLuDi(mealInfo.getMealBrLuDi())
                .build();
        log.info("foodInfo: {}", inputMealData);

        return inputMealData;
    }



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

//            List<FoodIdAndAmountDto> detail = new ArrayList<>();
//            for (int  i=0; i < mealInfo.getFoodDbId().size(); i++)
//            {
//                detail.add( FoodIdAndAmountDto.builder()
//                                .foodDbId(mealInfo.getFoodDbId().get(i))
//                                .foodAmount(mealInfo.getAmount().get(i))
//                                .build() );
//            }
//        InputMealCategoryReq inputMealData = InputMealCategoryReq.builder()
//                .memberNoLogin(memberNoLogin)
//                .mealDay(mealInfo.getMealDay())
//                .mealDetails(detail)
//                .mealBlLuDi(mealInfo.getMealBrLuDi())
//                .build();
//        log.info("foodInfo: {}", inputMealData);
//
//
//            int res = mealMapper.inputDayMealData( inputMealData);
//            log.info("inputDayMealCategory 성공 실패 : {}", res);
            InputMealCategoryReq inputMealData = indataMealCategory(memberNoLogin,mealInfo);

            int res = mealMapper.inputDayMealData( inputMealData);
            log.info("inputDayMealCategory 성공 실패 : {}", res);

            InpuMealDetailDto sumData = calculateTotal(mealInfo,memberNoLogin);
            int result = mealMapper.inputTotalCalorie(sumData);
            log.info("inputDayMealDetail 작동하냐 : {}", result);
//        List<GetFoodInfoAllRes> result = mealMapper.getDetailFoodInfo(mealInfo.getFoodDbId());
//
//
//            InpuMealDetailDto sumData = new InpuMealDetailDto();
//            sumData.setMealDay(mealInfo.getMealDay());
//            sumData.setMemberNoLogin(memberNoLogin);
//            sumData.setMealBrLuDi(mealInfo.getMealBrLuDi());
//
//            for (int i = 0; i < result.size(); i++) {
//                float amount = mealInfo.getAmount().get(i);
//                GetFoodInfoAllRes food = result.get(i);
//
//                sumData.setTotalCalorie((int)(sumData.getTotalCalorie() + (food.getCalorie() / 100.0) * amount));
//                log.info("sumData: {} {} {} ", sumData.getTotalCalorie() ,food.getCalorie() ,amount);
//                sumData.setTotalProtein((float)(sumData.getTotalProtein() + (food.getProtein() / 100.0) * amount));
//                sumData.setTotalFat((float)(sumData.getTotalFat() + (food.getFat() / 100.0) * amount));
//                sumData.setTotalCarbohydrate((float)(sumData.getTotalCarbohydrate() + (food.getCarbohydrate() / 100.0) * amount));
//                sumData.setTotalSugar((float)(sumData.getTotalSugar() + (food.getSugar() / 100.0) * amount));
//                sumData.setTotalNatrium((float)(sumData.getTotalNatrium() + (food.getNatrium() / 100.0) * amount));
//            }
//            return  mealMapper.inputTotalCalorie(sumData);
        }
        return 0;
    }

    List<GetMealListRes> getDataByMemberNoId(GetMealListReq getData){

        return mealMapper.getDataByMemberNoId(getData);
    };

    int modifyMeal (Integer memberNoLogin , findFoodDetailInfoReq mealInfo)
    {
        if(memberNoLogin ==null)
        {
            return 0;
        }
        if (mealInfo.getFoodDbId() != null && !mealInfo.getFoodDbId().isEmpty()) {
            InputMealCategoryReq inputMealData = indataMealCategory(memberNoLogin,mealInfo);
            int res = mealMapper.deleteMealCategory( inputMealData);
            log.info("deleteMealCategory 성공 실패 : {}", res);

            for (int i = 0; i < inputMealData.getMealDetails().size() ; i++) {
                res = mealMapper.modifyByMealDayAndMealBrLuDi(inputMealData, inputMealData.getMealDetails().get(i).getFoodDbId(),inputMealData.getMealDetails().get(i).getFoodAmount());
                log.info("modifyByMealDayAndMealBrLuDi 성공 실패 : {}", res);
            }
            InpuMealDetailDto sumData = calculateTotal(mealInfo,memberNoLogin);
            int result = mealMapper.modifyByMealTotalAndMealBrLuDi(sumData);
            log.info("modifyByMealTotalAndMealBrLuDi 작동하냐 : {}", result);

        }
        else
        {
            int res = mealMapper.deleteMealTotal(memberNoLogin,mealInfo);
            res = mealMapper.deleteMealCategoryIn( memberNoLogin,mealInfo);
        }


        return 0;
    }


}
