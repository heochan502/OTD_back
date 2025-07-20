package com.otd.onetoday_back.weather;

import com.otd.onetoday_back.weather.model.LocalNameGetReq;
import com.otd.onetoday_back.weather.model.LocalNameGetRes;
import com.otd.onetoday_back.weather.model.dto.WeatherDto;
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

    @GetMapping
    public ResponseEntity<?> localNameAll(LocalNameGetReq req){
        List<LocalNameGetRes> localNameAll = weatherService.getLocalNameAll(req);
        return ResponseEntity.ok(localNameAll);
    }

    @GetMapping("/{memberId}")
    public WeatherDto getWeather(@PathVariable int memberId) {
        log.info("memberId = {}", memberId);
        return weatherService.getWeatherByMemberId(memberId);
    }
}
