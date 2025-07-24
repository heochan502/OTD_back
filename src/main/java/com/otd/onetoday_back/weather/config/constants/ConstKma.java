package com.otd.onetoday_back.weather.config.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "constants.feign-client.weather-api")
public class ConstKma {
    private final String serviceKey;
    private final String dataType;
}
