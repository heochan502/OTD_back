package com.otd.onetoday_back.account;

import com.otd.onetoday_back.account.model.*;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {

    AccountLoginRes findByLogin( AccountLoginReq req);
    int save(AccountJoinReq req);
    AccountProfileRes findProfileById(int memberNoLogin);
    int updateProfile(memberUpdateDto dto);
    int existsByMemberId(String memberId);
    int existsByEmail(String email);
    int existsByMemberNick(String memberNick);
    String findPasswordByMemberNo(int memberNoLogin);
    int updatePassword(int memberNoLogin, String newPassword);
    int deleteById(int memberNoLogin);
    int deleteMemberLocationByMemberId(int memberNoLogin);

}