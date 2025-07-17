package com.otd.onetoday_back.weather;

import com.otd.onetoday_back.weather.model.feignclient.ultraSrtFcst.WeatherUltraSrtFcstReq;
import com.otd.onetoday_back.weather.model.feignclient.ultraSrtFcst.data.FcstResponseParent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/ultra_srt_fcst")
    public FcstResponseParent getUltraSrtFcst(@ModelAttribute WeatherUltraSrtFcstReq req) {
        log.info("req: {}", req);
        return weatherService.ultraSrtNcst(req);
    }
}
