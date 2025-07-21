package com.otd.onetoday_back.account.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AccountLoginRes {
    private int memberNoLogin;
    private String memberId;
    private String email;
    private String name;
    private String birthDate;
    private String memberNick;
    @JsonIgnore // 출력안됨
    private String memberPw;
}
