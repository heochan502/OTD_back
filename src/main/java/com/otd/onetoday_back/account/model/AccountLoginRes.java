package com.otd.onetoday_back.account.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class AccountLoginRes {
    private int memberNoLogin;
    @JsonIgnore // 출력안됨
    private String memberPw;
}
