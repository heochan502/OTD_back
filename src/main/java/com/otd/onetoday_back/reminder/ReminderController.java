package com.otd.onetoday_back.reminder;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.util.HttpUtils;
import com.otd.onetoday_back.reminder.model.ReminderPostReq;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/OTD/reminder")
public class ReminderController {
    private final ReminderService reminderService;

    @PostMapping
    public ResponseEntity<?> postReminder(HttpServletRequest httpReq, @RequestBody ReminderPostReq req){
        log.info("req:{}", req);
        int memberId = (int) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        req.setMemberId(memberId);
        int rusult = reminderService.postReminder(req);
        return ResponseEntity.ok(rusult);
    }
}
