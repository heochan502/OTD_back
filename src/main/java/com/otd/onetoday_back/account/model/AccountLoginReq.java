package com.otd.onetoday_back.account.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AccountLoginReq {
    private String memberId;
    private String memberPw;
}
