package com.otd.onetoday_back.weather;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.util.HttpUtils;
import com.otd.onetoday_back.weather.location.LocationService;
import com.otd.onetoday_back.weather.location.model.LocalNameGetReq;
import com.otd.onetoday_back.weather.location.model.LocalNameGetRes;
import com.otd.onetoday_back.weather.model.WeatherDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/OTD/weather")
public class WeatherController {
    private final WeatherService weatherService;
    private final LocationService locationService;

//    @GetMapping("/{memberId}")
//    public WeatherDto getWeather(@PathVariable int memberId) {
//        log.info("memberId = {}", memberId);
//        return weatherService.getWeatherByMemberId(memberId);
//    }

    @GetMapping
    public WeatherDto getWeather(HttpSession session) {
        Integer memberId = (Integer)session.getAttribute("memberId");
        return weatherService.getWeatherByMemberId(memberId);
    }
}
