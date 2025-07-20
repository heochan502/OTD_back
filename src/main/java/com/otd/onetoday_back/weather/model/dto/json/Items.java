package com.otd.onetoday_back.weather.model.dto.json;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Items {
    private List<Item> item;
}
