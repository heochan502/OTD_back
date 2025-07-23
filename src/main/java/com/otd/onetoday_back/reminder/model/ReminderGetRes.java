package com.otd.onetoday_back.reminder.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ReminderGetRes {
    private int id;
    private String title;
    private String content;
    private String date;
    private boolean repeat;
    private boolean alarm;
    private List<Integer> repeatDow;
}
