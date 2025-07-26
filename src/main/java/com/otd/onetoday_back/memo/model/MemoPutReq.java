package com.otd.onetoday_back.memo.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemoPutReq {
    private int id;
    private String title;
    private String content;
    private int memberNoLogin;
}
