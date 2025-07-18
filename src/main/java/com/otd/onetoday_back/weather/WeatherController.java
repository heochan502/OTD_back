package com.otd.onetoday_back.weather;

import com.otd.onetoday_back.weather.model.LocalNameGetRes;
import com.otd.onetoday_back.weather.model.WeatherDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<?> localNameAll(){
        List<LocalNameGetRes> localNameAll = weatherService.getLocalNameAll();
        return ResponseEntity.ok(localNameAll);
    }

}
