package com.otd.onetoday_back.reminder;

import com.otd.onetoday_back.reminder.model.ReminderPostReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReminderMapper {
    int post(ReminderPostReq req);
}
