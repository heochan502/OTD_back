package com.otd.onetoday_back.weather.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class BaseTime {

    public static String[] getBaseDateTime() {
        LocalDateTime now = LocalDateTime.now();

        int hour = now.getHour();
        int minute = now.getMinute();

        log.info("hour = {} minute = {}", hour, minute);

        // 초단기실황 발표는 매시각 40분 -> 이전 데이터 사용
        if (minute < 40) {
            hour -= 1;
            // 00시 넘어가면 전 날 23시 데이터 사용
            if (hour < 0) {
                hour = 23;
                now = now.minusDays(1);
            }
        }
        // 날짜는 yyyyMMdd로 포맷팅
        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 시간은 항상 시각 + 00분 형식으로 설정
        String baseTime = String.format("%02d00", hour);

        return new String[]{baseDate, baseTime};
    }

    // 단기예보용
    public static String[] getBaseTimeV() {
        LocalDateTime now = LocalDateTime.now();

        // 정해진 발표시간
        List<Integer> Hours = List.of(2, 5, 8, 11, 14, 17, 20, 23);

        int hour = now.getHour();
        int minute = now.getMinute();

        // 기본 값
        int baseHour = 2;
        for (int h : Hours) {
            if (hour > h || (hour == h && minute >= 10)) {
                baseHour = h;
            }
        }

        // 00~02 10분일때 전 날 23시로 이동
        if (hour < 2 || (hour == 2 && minute < 10)) {
            now = now.minusDays(1);
            baseHour = 23;
        }

        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = String.format("%02d00", baseHour);

        return new String[]{baseDate, baseTime};
    }
}
