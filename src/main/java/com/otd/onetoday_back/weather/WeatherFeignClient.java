package com.otd.onetoday_back.weather;
// mapper 역할

import com.otd.onetoday_back.weather.model.feignclient.ultraSrtFcst.data.FcstResponseParent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherApi"
            , url = "${constants.feign-client.weather-api.url}")
public interface WeatherFeignClient {

    @GetMapping("/getUltraSrtFcst")
    FcstResponseParent getUltraSrtFcst(@RequestParam String serviceKey,
                                       @RequestParam String dataType,
                                       @RequestParam("base_date") String baseDate,
                                       @RequestParam("base_time") String baseTime,
                                       @RequestParam int nx,
                                       @RequestParam int ny);

}
