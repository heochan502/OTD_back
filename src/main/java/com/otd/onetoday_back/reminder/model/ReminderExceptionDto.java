package com.otd.onetoday_back.reminder.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReminderExceptionDto {
  private int id;
  private String exceptionDate;
  private String endDate;

}
