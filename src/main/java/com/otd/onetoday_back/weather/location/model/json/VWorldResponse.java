package com.otd.onetoday_back.weather.location.model.json;

import lombok.Data;

import java.util.List;

@Data
public class VWorldResponse {
    private VWorldInnerResponse response;

    @Data
    public static class VWorldInnerResponse {
        private String status;
        private VWorldResult result;
    }

    @Data
    public static class VWorldResult {
        private List<VWorldItem> items;
    }

    @Data
    public static class VWorldItem {
        private String title;
        private VWorldAddress address;
        private VWorldPoint point;
    }

    @Data
    public static class VWorldAddress {
        private String road;
        private String parcel;
    }

    @Data
    public static class VWorldPoint {
        private String x; // 경도
        private String y; // 위도
    }
}
