package com.otd.onetoday_back.reminder;

import com.otd.onetoday_back.reminder.model.ReminderPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderService {
    private final ReminderMapper reminderMapper;

    public int postReminder(ReminderPostReq req){
        return reminderMapper.post(req);

    }
}
