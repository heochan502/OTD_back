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
                || !StringUtils.hasLength(req.getMemberNick())
                || !StringUtils.hasLength(req.getGender())) {
            return ResponseEntity.badRequest().build();
        }

        if (!"M".equals(req.getGender()) && !"F".equals(req.getGender())) {
            return ResponseEntity.badRequest().build();
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

    @PutMapping("/profile/detail")
    public ResponseEntity<?> updateProfile(
            HttpServletRequest httpReq,
            @RequestBody memberUpdateDto dto) {

        Integer memberNoLogin = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        if (memberNoLogin == null) {
            return ResponseEntity.status(401).build();
        }
        AccountProfileRes result = accountService.updateProfile(memberNoLogin.intValue(), dto);
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
    public ResponseEntity<Map<String, Boolean>> checkMemberId(@PathVariable String memberId) {
        boolean available = !accountService.existsByMemberId(memberId);
        return ResponseEntity.ok(Map.of("available", available));
    }

    @GetMapping("/check/email/{email}")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@PathVariable String email) {
        boolean available = !accountService.existsByEmail(email);
        return ResponseEntity.ok(Map.of("available", available));
    }

    @GetMapping("/check/nickname/{nickname}")
    public ResponseEntity<Map<String, Boolean>> checkNickname(@PathVariable String nickname) {
        boolean available = !accountService.existsByMemberNick(nickname);
        return ResponseEntity.ok(Map.of("available", available));
    }


    @PutMapping("/profile/password")
    public ResponseEntity<?> changePassword(
            HttpServletRequest httpReq,
            @RequestBody PasswordChangeDto dto) {

        if (!StringUtils.hasLength(dto.getCurrentPassword())
                || !StringUtils.hasLength(dto.getNewPassword())) {
            return ResponseEntity.badRequest().build();
        }

        Integer memberNoLogin = (Integer) HttpUtils.getSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        if (memberNoLogin == null) {
            return ResponseEntity.status(401).build();
        }

        int result = accountService.changePassword(memberNoLogin.intValue(), dto);

        if (result == 1) {
            return ResponseEntity.ok(result);
        } else if (result == -1) {
            return ResponseEntity.status(401).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpReq) {
        HttpUtils.removeSessionValue(httpReq, AccountConstants.MEMBER_ID_NAME);
        return ResponseEntity.ok(1);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMember(@RequestParam int memberNoLogin, HttpSession session) {
        log.info("memberNoLogin={}", memberNoLogin);
        int result = accountService.deleteById(memberNoLogin);
        session.invalidate();
        return ResponseEntity.ok(result);
    }
}