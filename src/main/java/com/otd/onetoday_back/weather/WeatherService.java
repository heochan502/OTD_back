package com.otd.onetoday_back.weather;

import com.otd.onetoday_back.weather.config.constants.ConstWeatherApi;
import com.otd.onetoday_back.weather.model.feignclient.ultraSrtFcst.WeatherUltraSrtFcstReq;
import com.otd.onetoday_back.weather.model.feignclient.ultraSrtFcst.data.FcstResponseParent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    private final WeatherFeignClient weatherFeignClient;
    private final ConstWeatherApi constWeatherApi;

    public FcstResponseParent ultraSrtNcst(WeatherUltraSrtFcstReq req){
        return weatherFeignClient.getUltraSrtFcst(
                constWeatherApi.getServiceKey(), constWeatherApi.getDataType(),
                req.getBaseDate(), req.getBaseTime(),  req.getNx(), req.getNy());
    }

}
