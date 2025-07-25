package com.otd.onetoday_back.meal;


import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.util.HttpUtils;
import com.otd.onetoday_back.meal.model.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/meal")
@Slf4j
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
//        else  (foodInfo.getFoodCategory() == null || foodInfo.getFoodCategory().isEmpty()) {
        else {
            List<FindFoodNameRes> res =  mealService.findFoodName(foodInfo);
            return ResponseEntity.ok(res);
        }
//        else
//        {
//            List<FindFoodNameRes> res  = mealService.findFoodName(foodInfo);
//            return ResponseEntity.ok(res);
//        }
//        log.info("memeber id {} : foodname {}", memberId, foodName);
//        List<findFoodNameRes> res = mealService.findFoodName(foodInfo);
//        log.info("res : {}", res);
//        return null;
    }
    @GetMapping("/calcul")
    public ResponseEntity<?> mealCalculation(HttpServletRequest httpReq, @ModelAttribute FindFoodNameReq foodInfo)
    {
        Integer memberId = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        log.info("foodInfo: {}", foodInfo);
        FindMealCalorieRes res = mealService.findMealCalorie(foodInfo);
        return ResponseEntity.ok(res);

//        log.info("memeber id {} : foodname {}", memberId, foodName);
//        List<findFoodNameRes> res = mealService.findFoodName(foodInfo);
//        log.info("res : {}", res);
//        return null;
    }
}
