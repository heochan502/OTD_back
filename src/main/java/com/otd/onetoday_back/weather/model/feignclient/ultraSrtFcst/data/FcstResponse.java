package com.otd.onetoday_back.weather.model.feignclient.ultraSrtFcst.data;

import feign.Body;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FcstResponse {
    private Body body;
}
