package com.otd.onetoday_back.reminder.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString

public class ReminderPostPutReq {
    private int id;
    private int memberId;
    private String title;
    private String content;
    private String startDate;
    private boolean repeat;
    private boolean alarm;
    private List<Integer> repeatDow;
}
