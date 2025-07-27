package com.otd.onetoday_back.reminder;

import com.otd.onetoday_back.reminder.model.ReminderGetReq;
import com.otd.onetoday_back.reminder.model.ReminderGetRes;
import com.otd.onetoday_back.reminder.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderService {
    private final ReminderMapper reminderMapper;

    public int postReminder(ReminderPostPutReq req){
        return reminderMapper.post(req);

    }
    public int postDow(ReminderPostPutReq req){
        return reminderMapper.postDow(req);
    }

    public List<ReminderGetRes> findByYearAndMonth(ReminderGetReq req){
        return reminderMapper.findByYearAndMonth(req);
    }

    public int modify(ReminderPostPutReq req){
        return reminderMapper.modify(req);
    }

    public int modifyDow(ReminderPostPutReq req){
        int result = reminderMapper.deleteDow(req.getId());
        int result2 = reminderMapper.postDow(req);
        return result + result2;
    }

    public int deleteById(int id){
        return reminderMapper.deleteById(id);
    }
}
