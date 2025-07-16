package com.otd.onetoday_back.account.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountLoginRes {
    private int accountId;
    @JsonIgnore // 출력안됨
    private String loginPw;
}
