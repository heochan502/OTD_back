package com.otd.onetoday_back.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otd.onetoday_back.weather.config.constants.ConstKma;
import com.otd.onetoday_back.weather.location.model.LocationDto;
import com.otd.onetoday_back.weather.model.WeatherDto;
import com.otd.onetoday_back.weather.model.json.Item;
import com.otd.onetoday_back.weather.model.json.ResponseParent;
import com.otd.onetoday_back.weather.util.BaseTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final WeatherMapper weatherMapper;
    private final WeatherFeignClient weatherFeignClient;
    private final ConstKma constKma;

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 파싱용

    private String Sky(String sky) {
        return switch (sky) {
            case "1" -> "맑음";
            case "3" -> "구름 많음";
            case "4" -> "흐림";
            default -> "알 수 없음";
        };
    }

    public WeatherDto getWeatherByMemberId(int memberId) {
        LocationDto location = weatherMapper.findLocalByMemberId(memberId);
        log.info("memberId: {}", memberId);
        // 실시간 날짜/시간
        String[] base = BaseTime.getBaseDateTime();

        String baseDate = base[0];
        String baseTime = base[1];

        // Feign 호출
        String response = weatherFeignClient.getUltraSrtFcst(
                constKma.getServiceKey(),
                constKma.getDataType(),
                baseDate,
                baseTime,
                location.getNx(),
                location.getNy(),
                30,
                1
        );
        try {
            ResponseParent weatherApi = objectMapper.readValue(response, ResponseParent.class);
            List<Item> items = weatherApi.getResponse().getBody().getItems().getItem();

            String temp = null;
            String sky = null;
            log.info("items = {}", items);

            // 이미 수집된 자료는 더이상 받지 않는다
            Set<String> firstIndex = new HashSet<>();

            for (Item item : items) {
                switch (item.getCategory()) {
                    case "T1H":
                        if (!firstIndex.contains("T1H")) {
                            temp = item.getFcstValue();
                            firstIndex.add("T1H");
                        }
                        break;
                    case "SKY":
                        if (!firstIndex.contains("SKY")) {
                            sky = Sky(item.getFcstValue());
                            firstIndex.add("SKY");
                        }
                        break;
                }
            }

            LocationDto local = new LocationDto();
            local.setCity(location.getCity());
            local.setCounty(location.getCounty());
            local.setTown(location.getTown());

            WeatherDto dto = new WeatherDto();
            dto.setBaseTime(baseDate + " " + baseTime);
            dto.setTemperature(temp);
            dto.setCondition(sky);
            dto.setLocalName(local.getCity() + " " + local.getCounty() + " "+ local.getTown());

            return dto;

        } catch (Exception e) {
            throw new RuntimeException("날씨 API 실패", e);
        }
    }
}
