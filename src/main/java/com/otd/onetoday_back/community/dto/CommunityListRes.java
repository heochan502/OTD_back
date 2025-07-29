package com.otd.onetoday_back.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

//페이징을 위한 dto
@Data
@AllArgsConstructor
public class CommunityListRes {
    private List<CommunityPostRes> content;
    private int totalCount;
}
