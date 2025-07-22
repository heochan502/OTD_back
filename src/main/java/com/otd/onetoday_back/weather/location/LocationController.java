package com.otd.onetoday_back.weather.location;

import com.otd.onetoday_back.weather.location.model.LocalNameGetReq;
import com.otd.onetoday_back.weather.location.model.LocalNameGetRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/location")
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<?> localNameAll(LocalNameGetReq req){
        List<LocalNameGetRes> localNameAll = locationService.getLocalNameAll(req);
        return ResponseEntity.ok(localNameAll);
    }
}
