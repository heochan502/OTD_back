package com.otd.onetoday_back.weather.config.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "constants.feign-client.weather-api")
public class ConstKma {
    public final String serviceKey;
    public final String dataType;
}
