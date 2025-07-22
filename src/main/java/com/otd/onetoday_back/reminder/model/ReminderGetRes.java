package com.otd.onetoday_back.reminder.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ReminderGetRes {
  private int id;
  private String title;
  private String date;
}
