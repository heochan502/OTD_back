package com.otd.onetoday_back.weather.util;

public class GeoTrans {
    public static final int TO_GRID = 0;

    private static final double RE = 6371.00877; // 지구 반경(km)
    private static final double GRID = 5.0; // 격자 간격(km)
    private static final double SLAT1 = 30.0; // 표준위도1
    private static final double SLAT2 = 60.0; // 표준위도2
    private static final double OLON = 126.0; // 기준 경도
    private static final double OLAT = 38.0; // 기준 위도
    private static final double XO = 43; // 기준 X좌표
    private static final double YO = 136; // 기준 Y좌표

    public static class LatXLngY {
        public double lat;
        public double lon;
        public int nx;
        public int ny;
    }

    public static LatXLngY convertGRID_GPS(double lat, double lon) {
        double DEGRAD = Math.PI / 180.0;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) /
                Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);

        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;

        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        LatXLngY rs = new LatXLngY();
        rs.lat = lat;
        rs.lon = lon;

        double ra = Math.tan(Math.PI * 0.25 + (lat) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        double theta = lon * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;
        rs.nx = (int) Math.floor(ra * Math.sin(theta) + XO + 0.5);
        rs.ny = (int) Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);

        return rs;
    }
}
