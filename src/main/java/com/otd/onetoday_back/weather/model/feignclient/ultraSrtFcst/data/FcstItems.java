package com.otd.onetoday_back.weather.model.feignclient.ultraSrtFcst.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FcstItems {
    private List<FcstItem> item;
}
