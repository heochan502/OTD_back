package com.otd.onetoday_back.reminder.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class ReminderPostReq {
    private int memberId;
    private String title;
    private String content;
    private String date;
    private boolean repeat;
    private boolean alarm;
}
