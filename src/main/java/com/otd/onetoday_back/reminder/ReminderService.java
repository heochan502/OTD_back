package com.otd.onetoday_back.reminder;

import com.otd.onetoday_back.reminder.model.ReminderGetReq;
import com.otd.onetoday_back.reminder.model.ReminderGetRes;
import com.otd.onetoday_back.reminder.model.ReminderPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderService {
    private final ReminderMapper reminderMapper;

    public int postReminder(ReminderPostReq req){
        return reminderMapper.post(req);

    }
    public int postDow(ReminderPostReq req){
        return reminderMapper.postDow(req);
    }

    public List<ReminderGetRes> getMonth(ReminderGetReq req){
        return reminderMapper.findByMonth(req);
    }
}
