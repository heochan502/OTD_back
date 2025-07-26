package com.otd.onetoday_back.account;

import com.otd.onetoday_back.account.model.*;
import com.otd.onetoday_back.account.etc.*;
import com.otd.onetoday_back.common.util.HttpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/OTD/user")
public class AccountController {
    private final AccountService accountService;


    @PostMapping("/signup")
    public ResponseEntity<?> join(@RequestBody AccountJoinReq req) {
        if (!StringUtils.hasLength(req.getMemberId())
                || !StringUtils.hasLength(req.getMemberPw())
                || !StringUtils.hasLength(req.getEmail())
                || !StringUtils.hasLength(req.getName())
                || !StringUtils.hasLength(req.getMemberNick())) {
            return ResponseEntity.badRequest().build(); //state: 400
        }
        log.info(" changed  : {}", req.getMemberNick());
        int result = accountService.join(req);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile(HttpServletRequest httpReq) {
        Integer memberNoLogin = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        if (memberNoLogin == null) {
            return ResponseEntity.status(401).build();
        }

        AccountProfileRes profile = accountService.getProfile(memberNoLogin);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> updateProfile(
            HttpServletRequest httpReq,
            @RequestBody memberUpdateDto dto) {

        Integer memberNoLogin = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        if (memberNoLogin == null) {
            return ResponseEntity.status(401).build();
        }
        AccountProfileRes result = accountService.updateProfile(memberNoLogin.longValue(), dto);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }

@PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest httpReq, @RequestBody AccountLoginReq req) {

        log.info(" changed  : {}", req);

        AccountLoginRes result = accountService.login(req);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        //세션 처리
        HttpUtils.setSession(httpReq, AccountConstants.MEMBER_ID_NAME, result.getMemberNoLogin());//확인하기

        log.info(" MEMBER_ID_NAME  : {}", result.getMemberNoLogin());


        return ResponseEntity.ok(result);
    }

    @GetMapping("/check")

    public ResponseEntity<?> check(HttpServletRequest httpReq) {
        Integer id = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        log.info("id: {}", id);

        return ResponseEntity.ok(id);
    }


    @GetMapping("/check/id/{memberId}")
    public ResponseEntity<?> checkMemberId(@PathVariable String memberId) {
        boolean exists = accountService.existsByMemberId(memberId);
        Map<String, Object> response = new HashMap<>();
        response.put("available", !exists);
        response.put("message", exists ? "이미 사용중인 아이디입니다." : "사용 가능한 아이디입니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check/email/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable String email) {
        boolean exists = accountService.existsByEmail(email);
        Map<String, Object> response = new HashMap<>();
        response.put("available", !exists);
        response.put("message", exists ? "이미 사용중인 이메일입니다." : "사용 가능한 이메일입니다.");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/check/nickname/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname) {
        boolean exists = accountService.existsByMemberNick(nickname);
        Map<String, Object> response = new HashMap<>();
        response.put("available", !exists);
        response.put("message", exists ? "이미 사용중인 닉네임입니다." : "사용 가능한 닉네임입니다.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpReq) {
        HttpUtils.removeSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        return ResponseEntity.ok(1);
    }
}