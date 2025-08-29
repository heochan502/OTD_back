package com.otd.onetoday_back.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otd.onetoday_back.account.model.memberUpdateDto;
import com.otd.onetoday_back.weather.config.constants.ConstKma;
import com.otd.onetoday_back.weather.location.model.LocationDto;
import com.otd.onetoday_back.weather.model.DailyWeather;
import com.otd.onetoday_back.weather.model.WeatherDto;
import com.otd.onetoday_back.weather.model.json.Item;
import com.otd.onetoday_back.weather.model.json.ResponseParent;
import com.otd.onetoday_back.weather.util.BaseTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
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
    private String Pty(String pty) {
        if (pty == null) return "없음";
        return switch (pty) {
            case "1" -> "비";
            case "2" -> "비/눈";
            case "3" -> "눈";
            case "5" -> "빗방울";
            case "6" -> "빗방울눈날림";
            case "7" -> "눈날림";
            default -> "없음";
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
            String ncstResponse = weatherFeignClient.getUltraSrtNcst(
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
            ResponseParent ncstWeatherApi = objectMapper.readValue(ncstResponse, ResponseParent.class);
            List<Item> ncstItems = ncstWeatherApi.getResponse().getBody().getItems().getItem();

            Map<String, String> ncstMap = new HashMap<>();

            for (Item item : ncstItems) {
                String category = item.getCategory();
                if ("T1H".equals(category) || "REH".equals(category) || "PTY".equals(category) || "RN1".equals(category)) {
                    ncstMap.put(category, item.getObsrValue());
                }
            }

            log.info("ncstItems = {}", ncstMap);

            // FeignClient 단기예보 호출
            String villageTMN = weatherFeignClient.getVilageFcst(
                    constKma.getServiceKey(),
                    constKma.getDataType(),
                    baseV[0],
                    "0200",
                    location.getNx(),
                    location.getNy(),
                    1,
                    100
            );

            String villageTMX = weatherFeignClient.getVilageFcst(
                    constKma.getServiceKey(),
                    constKma.getDataType(),
                    baseV[0],
                    "1100",
                    location.getNx(),
                    location.getNy(),
                    1,
                    100
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

            WeatherDto dto = WeatherDto.builder()
                    .baseTime(base[0] + " " + base[1])
                    .ncstTem(ncstMap.get("T1H"))
                    .ncstReh(ncstMap.get("REH"))
                    .ncstRh1(ncstMap.get("RN1"))
                    .ncstPty(Pty(ncstMap.get("PTY")))

                    .villageTmx(villageMap.get("TMX"))
                    .villageTmn(villageMap.get("TMN"))
                    .villagePop(villageMap.get("POP"))
                    .villageSky(Sky(villageMap.get("SKY")))

                    .localName(local.getCity() + " " + local.getCounty() + " "+ local.getTown())
                    .build();
            log.info("dto = {}", dto);

            return dto;

        } catch (Exception e) {
            log.error("날씨 API 실패", e);
            throw new RuntimeException("날씨 API 실패", e);
        }
    }

    public String formatString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyMMdd"));
    }

    public List<DailyWeather> getDailyWeather(int memberId) {
        LocationDto location = weatherMapper.findLocalByMemberId(memberId);
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        String baseDate = formatString(yesterday);
        try {
            String dailyWeather = weatherFeignClient.getVilageFcst(
                    constKma.getServiceKey(),
                    constKma.getDataType(),
                    baseDate,
                    "2300",
                    location.getNx(),
                    location.getNy(),
                    1,
                    288
            );

            ResponseParent dailyWeatherApi = objectMapper.readValue(dailyWeather, ResponseParent.class);
            List<Item> dailyItems = dailyWeatherApi.getResponse().getBody().getItems().getItem();
            Map<String, DailyWeather> dailyMap = new LinkedHashMap<>();

            for (Item item : dailyItems) {
                String dailyTime = item.getFcstDate() + item.getFcstTime();
                dailyMap.putIfAbsent(dailyTime, new DailyWeather());

                DailyWeather daily = dailyMap.get(dailyTime);
                daily.setFcstDate(item.getFcstDate());
                daily.setFcstTime(item.getFcstTime());
                switch (item.getCategory()) {
                    case "POP" -> daily.setPOP(item.getFcstValue());
                    case "SKY" -> daily.setSKY(Sky(item.getFcstValue()));
                    case "PTY" -> daily.setPTY(Pty(item.getFcstValue()));
                    case "TMP" -> daily.setTMP(item.getFcstValue());
                }
            }
            log.info("dailyMap = {}", dailyMap);
            return new ArrayList<>(dailyMap.values());
        } catch (Exception e) {
            log.error("날씨 API 실패", e);
            throw new RuntimeException("날씨 API 실패", e);
        }
    }

    public memberUpdateDto getNickName(int memberId) {
        return weatherMapper.getNickName(memberId);
    }

}
