package com.otd.onetoday_back.account;

import com.otd.onetoday_back.account.model.*;
import com.otd.onetoday_back.account.etc.*;
import com.otd.onetoday_back.common.util.HttpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;




@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/otd/user")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<?> join(@RequestBody AccountJoinReq req) {
        if(!StringUtils.hasLength(req.getName())
                || !StringUtils.hasLength(req.getLoginId())
                || !StringUtils.hasLength(req.getLoginPw())) {
            return ResponseEntity.badRequest().build(); //state: 400
        }

        int result =  accountService.join(req);
        return ResponseEntity.ok(result); //state: 200
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest httpReq, @RequestBody AccountLoginReq req) {
        AccountLoginRes result = accountService.login(req);
        if(result == null) {
            return ResponseEntity.notFound().build();
        }
        //세션 처리
        HttpUtils.setSession(httpReq, AccountConstants.MEMBER_ID_NAME, result.getAccountId());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/check")
    public ResponseEntity<?> check(HttpServletRequest httpReq) {
        Integer id = (Integer)HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
//        log.info("id: {}", id);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpReq) {
        HttpUtils.removeSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        return ResponseEntity.ok(1);
    }

}