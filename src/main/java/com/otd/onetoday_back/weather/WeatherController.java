package com.otd.onetoday_back.weather;

import com.otd.onetoday_back.account.model.memberUpdateDto;
import com.otd.onetoday_back.weather.location.LocationService;
import com.otd.onetoday_back.weather.model.SrtFcst;
import com.otd.onetoday_back.weather.model.WeatherDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/OTD/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping
    public WeatherDto getWeather(HttpSession session) {
        Integer memberId = (Integer)session.getAttribute("memberId");
        return weatherService.getWeatherByMemberId(memberId);
    }

    @GetMapping("/fcst")
    public List<SrtFcst> getSrtFcst(HttpSession session) {
        Integer memberId = (Integer)session.getAttribute("memberId");
        return weatherService.getSrtFcst(memberId);
    }

    @GetMapping("/info")
    public memberUpdateDto getWeatherInfo(HttpSession session) {
        Integer memberId = (Integer)session.getAttribute("memberId");
        return weatherService.getNickName(memberId);
    }

}