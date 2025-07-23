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
        //비밀번호 체크
        if( res == null ||!BCrypt.checkpw(req.getMemberPw(), res.getMemberPw()))
        {
            return null; // 비밀번호가 다르면 null 처리
        }
        return res;
    }

    public AccountProfileRes getProfile(int memberNoLogin) {
        return accountMapper.findProfileById(memberNoLogin);
    }

    public int updateProfile(AccountUpdateReq req) {
        return accountMapper.updateProfile(req);
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