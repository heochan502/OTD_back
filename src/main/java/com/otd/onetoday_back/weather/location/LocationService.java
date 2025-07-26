package com.otd.onetoday_back.weather.location;

import com.otd.onetoday_back.weather.location.model.LocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {
    private final LocationMapper locationMapper;

    public int insertLocation(LocationDto dto) {
        return locationMapper.insertMemberLocation(dto);
    }

    public List<LocationDto> getLocalList(LocationDto dto) {
        return locationMapper.getLocalList(dto);
    }
    public List<LocationDto> getLocalListByMemberId(int memberId) {
        return locationMapper.getLocalListByMemberId(memberId);
    }
    @Transactional
    public void selectLocation(int memberId, int localId) {
        // 기존 선택된 지역 전부 false 처리
        locationMapper.unselectAllByMemberId(memberId);

        // 새로 선택한 지역 true 처리
        locationMapper.updateSelectedLocation(memberId, localId);
    }
}
