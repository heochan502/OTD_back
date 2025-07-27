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
        AccountProfileRes currentProfile = accountMapper.findProfileById(memberNoLogin);
        if (currentProfile == null) {
            return null;
        }
        dto.setMemberNoLogin(memberNoLogin);

        int result = accountMapper.updateProfile(dto);
        if (result > 0) {
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