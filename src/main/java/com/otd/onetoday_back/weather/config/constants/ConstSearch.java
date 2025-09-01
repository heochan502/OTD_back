package com.otd.onetoday_back.weather.config.constants;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "constants.feign-client.search-api")
public class ConstSearch {
    public final String key;
    public final String service;
    public final String request;
    public final String category;
    public final String format;
}
