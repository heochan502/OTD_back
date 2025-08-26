package com.otd.onetoday_back.reminder.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReminderExceptionDto {
  private int memberId;
  private int id;
  private String endDate;
  private String exceptionDate;

}
