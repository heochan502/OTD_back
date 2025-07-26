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
    @GetMapping
    public ResponseEntity<?> getLocalList(HttpSession session) {
       Integer memberId = (Integer)session.getAttribute("memberId");
       List<LocationDto> list = locationService.getLocalListByMemberId(memberId);
       return ResponseEntity.ok(list);
    }

    @PutMapping("/select")
    public ResponseEntity<?> selectLocation(HttpSession session,
                                               @RequestBody LocationDto dto) {
        Integer memberId = (Integer)session.getAttribute("memberId");
        dto.setMemberId(memberId);
        boolean result = locationService.selectLocation(memberId, dto.getLocalId());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{localId}")
    public ResponseEntity<?> deleteLocation(HttpSession session, @PathVariable int localId){
        Integer memberId = (Integer)session.getAttribute("memberId");
        int result = locationService.deleteLocation(memberId, localId);
        return ResponseEntity.ok(result);
    }

}
