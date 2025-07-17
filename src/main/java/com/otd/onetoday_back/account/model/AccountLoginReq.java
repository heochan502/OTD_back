package com.otd.onetoday_back.account.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountLoginReq {
    private String memberId;
    private String memberPw;
}
