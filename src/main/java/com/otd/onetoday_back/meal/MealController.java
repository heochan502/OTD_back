package com.otd.onetoday_back.meal;


import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.util.HttpUtils;
import com.otd.onetoday_back.meal.model.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/meal")
@Slf4j
@Builder
public class MealController {
    private final MealService mealService;


    @GetMapping()
    public ResponseEntity<?> findFood(HttpServletRequest httpReq, @ModelAttribute FindFoodNameReq foodInfo)
    {
//        Integer memberId = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
//        log.info("foodInfo: {}", foodInfo);

        if (foodInfo.getFoodName() == null || foodInfo.getFoodName().isEmpty()) {
            List<FindFoodCategoryRes> res =  mealService.findFoodCategory(foodInfo);

            return ResponseEntity.ok(res);
        }
        else {
            List<FindFoodNameRes> res =  mealService.findFoodName(foodInfo);
            return ResponseEntity.ok(res);
        }

    }
    @PostMapping ("/saveMeal")
    public ResponseEntity<?> mealCalculation(HttpServletRequest httpReq, @RequestBody findFoodDetailInfoReq mealInfo)
    {
        Integer memberNoLogin = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        //들어오는 날짜 데이터 변조
//        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(mealInfo.getMealDay());
        log.info("foodInfo: {}", mealInfo);

//        return ResponseEntity.ok(res);
         int result = mealService.inputDayMealData( memberNoLogin,mealInfo);
         log.info("result: {}", result);
        return ResponseEntity.ok(1);
    }

    @GetMapping("/getMeal")
    public ResponseEntity<?> getMealList (HttpServletRequest httpReq, @ModelAttribute GetMealListReq getData)
    {
        Integer memberNoLogin = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        if (memberNoLogin == null) {
            return ResponseEntity.ok("로그인 안함");
        }

        getData.setMemberNoLogin(memberNoLogin);

        log.info("getMealList: {}", getData);
        List<GetMealListRes> result = mealService.getDataByMemberNoId(getData);
        log.info("result: {}", result);
        return ResponseEntity.ok(result);

    }
    @PutMapping("/modifyMeal")
    public ResponseEntity<?> modifyMeal(HttpServletRequest httpReq, @RequestBody findFoodDetailInfoReq mealInfo)
    {
        Integer memberNoLogin = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        //들어오는 날짜 데이터 변조
        log.info("foodInfo: {}", mealInfo);

//        return ResponseEntity.ok(res);
        int result = mealService.modifyMeal( memberNoLogin,mealInfo);
        log.info("result: {}", result);
        return ResponseEntity.ok(result);
//
    }

}
