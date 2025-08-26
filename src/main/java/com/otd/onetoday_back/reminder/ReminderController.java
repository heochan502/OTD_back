package com.otd.onetoday_back.reminder;

import com.otd.onetoday_back.account.etc.AccountConstants;
import com.otd.onetoday_back.common.util.HttpUtils;
import com.otd.onetoday_back.reminder.model.ReminderExceptionDto;
import com.otd.onetoday_back.reminder.model.ReminderGetReq;
import com.otd.onetoday_back.reminder.model.ReminderGetRes;
import com.otd.onetoday_back.reminder.model.ReminderPostPutReq;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<?> postReminder(HttpServletRequest httpReq, @RequestBody ReminderPostPutReq req){
        log.info("req:{}", req);
        Integer memberId = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        req.setMemberId(memberId);
        log.info("memberId:{}", memberId);
        int result = reminderService.postReminder(req);

        if(req.isRepeat()){
        int result2 = reminderService.postDow(req);
        return ResponseEntity.ok(result + result2);
        }
        log.info("result:{}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping()
    public ResponseEntity<?> getMonthReminder(HttpServletRequest httpReq, @ModelAttribute ReminderGetReq req){
        log.info("req:{}", req);
        Integer memberId = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        req.setMemberId(memberId);
        log.info("memberId:{}", memberId);
        List<ReminderGetRes> result = reminderService.findByYearAndMonth(req);
        log.info("result:{}", result);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<?> putReminder(HttpServletRequest httpReq, @RequestBody ReminderPostPutReq req){
        log.info("req:{}", req);
        Integer memberId = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        req.setMemberId(memberId);
        log.info("memberId:{}", memberId);
        int result = reminderService.modify(req);
        if(req.isRepeat()){
            int result2 = reminderService.modifyDow(req);
            return ResponseEntity.ok(result + result2);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/exception")
    public ResponseEntity<?> putReminder(HttpServletRequest httpReq, @RequestBody ReminderExceptionDto dto){
        log.info("dto:{}", dto);
        Integer memberId = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        dto.setMemberId(memberId);
        log.info("memberId:{}", memberId);
        int result = reminderService.modify(dto);
        return ResponseEntity.ok(result);
        // todo: post작업까지 하기
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteReminder(@PathVariable int id){
        int result = reminderService.deleteById(id);
        return ResponseEntity.ok(result);
    }


}
