package com.otd.onetoday_back.reminder.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReminderGetReq {
  private int memberId;
  private String year;
  private String month;
  private String day;
}
