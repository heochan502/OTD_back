package com.otd.onetoday_back.account.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountProfileReq {
    private int memberNoLogin;
    private String memberId;
}
