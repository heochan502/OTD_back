package com.otd.onetoday_back.weather.location;

import com.otd.onetoday_back.weather.location.model.LocationDto;
import com.otd.onetoday_back.weather.location.model.PostAddressReq;
import com.otd.onetoday_back.weather.location.model.SearchDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/location")
public class LocationController {
    private final LocationService locationSearchService;

    @GetMapping("/search")
    public List<SearchDto> search(@RequestParam String keyword) throws Exception {
        return locationSearchService.search(keyword);
    }

    @PostMapping("/post")
    public ResponseEntity<?> saveAddress(@RequestBody PostAddressReq req, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        req.setMemberId(memberId);

        try {
            locationSearchService.saveAddress(req);
            return ResponseEntity.ok("주소 저장 성공");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/list")
    public ResponseEntity<?> getAddressList(HttpSession session) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        log.info("<UNK> <UNK> <UNK> <UNK> <UNK> <UNK> {}", memberId);
        return ResponseEntity.ok(locationSearchService.getAddressList(memberId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable int id, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute("memberId");

        int result = locationSearchService.removeAddress(id, memberId);
        if (result > 0) {
            return ResponseEntity.ok("삭제되었습니다");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제에 실패했습니다");
        }
    }

    @PutMapping("/select/{addressId}")
    public ResponseEntity<?> selectAddress(@PathVariable int addressId, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        locationSearchService.selectAddress(memberId, addressId);
        return ResponseEntity.ok("선택 성공");
    }
    @GetMapping("/selected")
    public ResponseEntity<LocationDto> getSelectedAddress(HttpSession session) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        LocationDto dto = locationSearchService.getSelectedAddress(memberId);
        return ResponseEntity.ok(dto);
    }
}
