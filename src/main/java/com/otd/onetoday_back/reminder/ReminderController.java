package com.otd.onetoday_back.reminder;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.util.HttpUtils;
import com.otd.onetoday_back.reminder.model.ReminderGetReq;
import com.otd.onetoday_back.reminder.model.ReminderGetRes;
import com.otd.onetoday_back.reminder.model.ReminderPostReq;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/OTD/reminder")
public class ReminderController {
    private final ReminderService reminderService;

    @PostMapping()
    public ResponseEntity<?> postReminder(HttpServletRequest httpReq, @RequestBody ReminderPostReq req){
        log.info("req:{}", req);
        Integer memberId = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        req.setMemberId(memberId);
        log.info("memberId:{}", memberId);
        int result = reminderService.postReminder(req);
        if(req.isRepeat()){
        int result2 = reminderService.postDow(req);
        return ResponseEntity.ok(result + result2);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping()
    public ResponseEntity<?> getMonthReminder(HttpServletRequest httpReq, @ModelAttribute ReminderGetReq req){
        log.info("req:{}", req);
        Integer memberId = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        req.setMemberId(memberId);
        log.info("memberId:{}", memberId);
        List<ReminderGetRes> result = reminderService.getMonth(req);
        log.info("result:{}", result);
        return ResponseEntity.ok(result);
    }
}
