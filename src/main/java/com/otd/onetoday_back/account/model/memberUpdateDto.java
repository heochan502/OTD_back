package com.otd.onetoday_back.account.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class memberUpdateDto {
    private int memberNoLogin;
    private String memberId;
    private String email;
    private String name;
    private String birthDate;
    private String memberNick;
    private String memberImg;
    private String gender;
}
