package com.otd.onetoday_back.reminder.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class ReminderGetRes {
    private int memberId;
    private int id;
    private String title;
    private String content;
    private String created;
    private String startDate;
    private String endDate;
    private boolean repeat;
    private boolean alarm;
    private List<Integer> repeatDow;
    private List<String> exceptionDate;

    public void setRepeatDow(String repeatDowStr) {
        if (repeatDowStr == null || repeatDowStr.isEmpty()) {
            this.repeatDow = new ArrayList<>();
        } else {
            this.repeatDow = Arrays.stream(repeatDowStr.split(","))
                    .map(Integer::parseInt) // int로 형변환
                    .collect(Collectors.toList());
        }
    }

    public void setExceptionDate(String exceptionDateStr) {
        if(exceptionDateStr == null || exceptionDateStr.isEmpty()) {
            this.exceptionDate = new ArrayList<>();
        }else{
            this.exceptionDate = Arrays.stream(exceptionDateStr.split(","))
                    .collect(Collectors.toList());
        }
    }
}
