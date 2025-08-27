package com.otd.onetoday_back.account;

import com.otd.onetoday_back.account.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

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
    int updatePassword(Map<String, Object> params);
    int deleteById(int memberNoLogin);
    int deleteMemberLocationByMemberId(int memberNoLogin);

}