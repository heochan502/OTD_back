package com.otd.onetoday_back.weather;
// mapper 역할

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherApi",
            url = "${constants.feign-client.weather-api.url}")
public interface WeatherFeignClient {

    @GetMapping("/getUltraSrtFcst")
    String getUltraSrtFcst(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("dataType") String dataType,
            @RequestParam("base_date") String baseDate,
            @RequestParam("base_time") String baseTime,
            @RequestParam("nx") int nx,
            @RequestParam("ny") int ny,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("numOfRows") int numOfRows
    );

    @GetMapping("/getVilageFcst")
    String getVilageFcst(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("dataType") String dataType,
            @RequestParam("base_date") String baseDate,
            @RequestParam("base_time") String baseTime,
            @RequestParam("nx") int nx,
            @RequestParam("ny") int ny,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("numOfRows") int numOfRows
    );

    @GetMapping("/getUltraSrtNcst")
    String getUltraSrtNcst(
            @RequestParam("serviceKey") String serviceKey,
            @RequestParam("dataType") String dataType,
            @RequestParam("base_date") String baseDate,
            @RequestParam("base_time") String baseTime,
            @RequestParam("nx") int nx,
            @RequestParam("ny") int ny,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("numOfRows") int numOfRows
    );
}
