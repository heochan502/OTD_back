package com.otd.onetoday_back.account;

import com.otd.onetoday_back.account.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {

    AccountLoginRes findByLogin( AccountLoginReq req);
    int save(AccountJoinReq req);
    AccountProfileRes findProfileById(int memberNoLogin);
    int updateProfile(AccountUpdateReq req);
    int existsByMemberId(String memberId);
    int existsByEmail(String email);
    int existsByMemberNick(String memberNick);
}