package com.otd.onetoday_back.reminder;

import com.otd.onetoday_back.reminder.model.ReminderGetReq;
import com.otd.onetoday_back.reminder.model.*;
import com.otd.onetoday_back.reminder.model.ReminderGetRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReminderMapper {
    int post(ReminderPostPutReq req);
    int postDow(ReminderPostPutReq req);
    List<ReminderGetRes> findByMonth(ReminderGetReq req);
    List<ReminderGetDow> findByMonthAndRepeatTrue(int id);
    List<ReminderGetOneRes> findByDay(ReminderGetReq req);
    int modify(ReminderPostPutReq req);
    int deleteDow(int id);
    int deleteById(int id);
}
