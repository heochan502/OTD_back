package com.otd.onetoday_back.account.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
//기본생성자 만드는거
//@NoArgsConstructor
@AllArgsConstructor
public class AccountJoinReq {
    private String memberId;
    private String memberPw;
    private String email;
    private String name;
    private String birthDate;
    private String memberNick;



}
