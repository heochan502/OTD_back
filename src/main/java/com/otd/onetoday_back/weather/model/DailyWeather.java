package com.otd.onetoday_back.weather.model;


import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class DailyWeather {
    private String POP;
    private String SKY;
    private String PTY;
    private String TMP;

    private String fcstDate;
    private String fcstTime;
}
