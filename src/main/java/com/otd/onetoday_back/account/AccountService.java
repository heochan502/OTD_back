package com.otd.onetoday_back.account;


import com.otd.onetoday_back.account.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService {

    private final AccountMapper accountMapper;

    public int join(AccountJoinReq req)
    {
        String hashedPw = BCrypt.hashpw(req.getMemberPw(), BCrypt.gensalt());
        AccountJoinReq changedReq = new AccountJoinReq(req.getMemberId(),hashedPw,req.getEmail(),req.getName(),req.getBirthDate(),req.getMemberNick());
        log.info(" changed2  : {}" ,req.getMemberId());
        return accountMapper.save(changedReq);
    }

    public AccountLoginRes login(AccountLoginReq req)
    {
        AccountLoginRes res = accountMapper.findByLogin(req);
        log.info("AccountLoginRes: memberNoLogin={}, memberPw={}", res.getMemberNoLogin(), res.getMemberPw());
        log.info("id:" + req.getMemberId());
        if( res == null ||!BCrypt.checkpw(req.getMemberPw(), res.getMemberPw()))
        {
            return null;
        }
        return res;
    }
    public AccountProfileRes updateProfile(int memberNoLogin, memberUpdateDto dto) {
        // 현재 프로필 존재 여부 확인
        AccountProfileRes currentProfile = accountMapper.findProfileById(memberNoLogin);
        if (currentProfile == null) {
            return null;
        }

        // DTO에 회원 번호 설정
        dto.setMemberNoLogin(memberNoLogin);

        // 프로필 업데이트 실행
        int result = accountMapper.updateProfile(dto);
        if (result > 0) {
            // 업데이트 성공시 최신 데이터 반환
            return accountMapper.findProfileById(memberNoLogin);
        }
        return null;
    }
    public AccountProfileRes getProfile(int memberNoLogin) {
        return accountMapper.findProfileById(memberNoLogin);
    }

    public boolean existsByMemberId(String memberId) {
        return accountMapper.existsByMemberId(memberId) > 0;
    }

    public boolean existsByEmail(String email) {
        return accountMapper.existsByEmail(email) > 0;
    }

    public boolean existsByMemberNick(String memberNick) {
        return accountMapper.existsByMemberNick(memberNick) > 0;
    }

}