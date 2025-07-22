package com.otd.onetoday_back.weather;
// mapper 역할

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherApi",
            url = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0")
public interface WeatherFeignClient {

    @GetMapping("/getUltraSrtFcst")
    String getUltraSrtFcst(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("dataType") String dataType,
            @RequestParam("base_date") String baseDate,
            @RequestParam("base_time") String baseTime,
            @RequestParam("nx") int nx,
            @RequestParam("ny") int ny,
            @RequestParam("numOfRows") int numOfRows,
            @RequestParam("pageNo") int pageNo
    );

    @GetMapping("/getVilageFcst")
    String getVillageForecast(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("dataType") String dataType,
            @RequestParam("base_date") String baseDate,
            @RequestParam("base_time") String baseTime,
            @RequestParam("nx") int nx,
            @RequestParam("ny") int ny,
            @RequestParam("numOfRows") int numOfRows,
            @RequestParam("pageNo") int pageNo
    );
}
