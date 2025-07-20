package com.otd.onetoday_back.weather.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTime {

    public static String[] getBaseDateTime() {
        LocalDateTime now = LocalDateTime.now();

        int hour = now.getHour();
        int minute = now.getMinute();

        // 30분 이전 -> 이전 데이터 사용
        if (minute < 45) {
            hour -= 1;
        }
        // 00시 넘어가면 전 날 23시 데이터 사용
        if (hour < 0) {
            hour = 23;
            now = now.minusDays(1);
        }
        // 날짜는 yyyyMMdd로 포맷팅
        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 시간은 항상 **시각 + 30분** 형식으로 설정
        String baseTime = String.format("%02d30", hour);

        return new String[]{baseDate, baseTime};
    }

}
