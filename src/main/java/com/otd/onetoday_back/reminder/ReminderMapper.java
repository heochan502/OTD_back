package com.otd.onetoday_back.reminder;

import com.otd.onetoday_back.reminder.model.ReminderGetReq;
import com.otd.onetoday_back.reminder.model.ReminderGetRes;
import com.otd.onetoday_back.reminder.model.ReminderPostReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReminderMapper {
    int post(ReminderPostReq req);
    int postDow(ReminderPostReq req);
    List<ReminderGetRes> findByMonth(ReminderGetReq req);
}
