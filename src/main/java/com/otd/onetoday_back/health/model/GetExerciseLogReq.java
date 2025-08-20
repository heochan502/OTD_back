package com.otd.onetoday_back.health.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.BindParam;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@ToString
@NoArgsConstructor
public class GetExerciseLogReq {
    private Integer page;
    private Integer rowPerPage;

    public GetExerciseLogReq(Integer page, @RequestParam("row_per_page") Integer rowPerPage) {
        this.page = page;
        this.rowPerPage = rowPerPage;
    }
}
