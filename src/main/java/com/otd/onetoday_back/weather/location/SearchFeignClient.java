package com.otd.onetoday_back.weather.location;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "searchApi",
            url = "${constants.feign-client.search-api.url}")
public interface SearchFeignClient {

    @GetMapping
    String searchLocation(
            @RequestParam("key") String key,
            @RequestParam("service") String service,
            @RequestParam("request") String request,
            @RequestParam("type") String type,
            @RequestParam("query") String query,
            @RequestParam("format") String format,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );

    @GetMapping
    String searchAddress(
            @RequestParam("key") String key,
            @RequestParam("service") String service,
            @RequestParam("request") String request,
            @RequestParam("type") String type,
            @RequestParam("category") String category,
            @RequestParam("query") String query,
            @RequestParam("format") String format,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );
}
