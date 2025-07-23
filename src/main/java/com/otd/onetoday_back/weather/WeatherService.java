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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


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
    // 실시간 날짜/시간
    String[] base = BaseTime.getBaseDateTime(); // 실황
    String[] baseV = BaseTime.getBaseTimeV(); // 예보
    // 실시간 날씨
    public WeatherDto getWeatherByMemberId(int memberId) {
        LocationDto location = weatherMapper.findLocalByMemberId(memberId);
        log.info("memberId: {}", memberId);

        try {
            // FeignClient 초단기실황 호출
            String ultraSrtNcstResponse = weatherFeignClient.getUltraSrtNcst(
                    constKma.getServiceKey(),
                    constKma.getDataType(),
                    base[0],
                    base[1],
                    location.getNx(),
                    location.getNy(),
                    1,
                    10
            );
            // 초단기실황 파싱
            ResponseParent ultraWeatherApi = objectMapper.readValue(ultraSrtNcstResponse, ResponseParent.class);
            List<Item> ultraItems = ultraWeatherApi.getResponse().getBody().getItems().getItem();

            Map<String, String> ultraMap = new HashMap<>();

            for (Item item : ultraItems) {
                ultraMap.put(item.getCategory(), item.getObsrValue());
            }

            log.info("ultraItems = {}", ultraMap);

            // FeignClient 단기예보 호출
            String villageTMN = weatherFeignClient.getVilageFcst(
                    constKma.getServiceKey(),
                    constKma.getDataType(),
                    baseV[0],
                    "0200",
                    location.getNx(),
                    location.getNy(),
                    1,
                    300
            );

            String villageTMX = weatherFeignClient.getVilageFcst(
                    constKma.getServiceKey(),
                    constKma.getDataType(),
                    baseV[0],
                    "1100",
                    location.getNx(),
                    location.getNy(),
                    1,
                    300
            );
            // 단기예보 파싱
            Map<String, String> villageMap = new HashMap<>();

            ResponseParent tmnVillageApi = objectMapper.readValue(villageTMN, ResponseParent.class);
            for (Item item : tmnVillageApi.getResponse().getBody().getItems().getItem()) {
                if (item.getFcstDate().equals(baseV[0])
                    && item.getCategory().equals("TMN")
                    && item.getFcstTime().equals("0600")) {
                    villageMap.put("TMN", item.getFcstValue());
                    break;
                }
            }

            ResponseParent tmxVillageApi = objectMapper.readValue(villageTMX, ResponseParent.class);
            for (Item item : tmxVillageApi.getResponse().getBody().getItems().getItem()) {
                if (!item.getFcstDate().equals(baseV[0])) continue;
                String category = item.getCategory();

                if (category.equals("TMX") && item.getFcstTime().equals("1500")) {
                    villageMap.put("TMX", item.getFcstValue());
                } else if ((category.equals("POP") || category.equals("SKY")) && !villageMap.containsKey(category)) {
                    villageMap.put(category, item.getFcstValue());
                }
            }


            log.info("villageItems = {}", villageMap);
            // 값 저장
            LocationDto local = new LocationDto();
            local.setCity(location.getCity());
            local.setCounty(location.getCounty());
            local.setTown(location.getTown());

            WeatherDto dto = new WeatherDto();
            dto.setBaseTime(base[0] + " " + base[1]);
            dto.setTem(ultraMap.get("T1H"));
            dto.setReh(ultraMap.get("REH"));
            dto.setLocalName(local.getCity() + " " + local.getCounty() + " "+ local.getTown());

            dto.setTmx(villageMap.get("TMX"));
            dto.setTmn(villageMap.get("TMN"));
            dto.setPop(villageMap.get("POP"));
            dto.setSky(Sky(villageMap.get("SKY")));

            return dto;

        } catch (Exception e) {
            log.error("날씨 API 실패", e);
            throw new RuntimeException("날씨 API 실패", e);
        }
    }
}
