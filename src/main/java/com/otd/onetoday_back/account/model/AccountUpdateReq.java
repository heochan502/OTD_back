package com.otd.onetoday_back.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdateReq {

        private int memberNoLogin;
        private String email;
        private String name;
        private String birthDate;
        private String memberNick;
    }

