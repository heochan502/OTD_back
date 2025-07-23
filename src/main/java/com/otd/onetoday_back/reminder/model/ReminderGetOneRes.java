package com.otd.onetoday_back.reminder.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ReminderGetOneRes {
  private int id;
  private String title;
  private String date;
}
