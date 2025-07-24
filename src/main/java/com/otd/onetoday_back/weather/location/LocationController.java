package com.otd.onetoday_back.weather.location;

import com.otd.onetoday_back.weather.location.model.LocationDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/location")
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/save")
    public ResponseEntity<?> insertLocation(HttpSession session,
                                            @RequestBody LocationDto dto) {
        Integer memberId = (Integer)session.getAttribute("memberId");
        dto.setMemberId(memberId);
        int result = locationService.insertLocation(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<?> localNameAll(LocationDto keyword) {
        List<LocationDto> list = locationService.getLocalList(keyword);
        return ResponseEntity.ok(list);
    }
}
