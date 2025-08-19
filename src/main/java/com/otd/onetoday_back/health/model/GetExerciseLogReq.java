package com.otd.onetoday_back.health.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@ToString
public class GetExerciseLogReq {
    private Integer page;
    private Integer rowPerPage;

    public GetExerciseLogReq(Integer page, @BindParam("row_per_page") Integer rowPerPage) {
        this.page = page;
        this.rowPerPage = rowPerPage;
    }
}
