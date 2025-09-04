package com.otd.onetoday_back.weather.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otd.onetoday_back.weather.config.constants.ConstSearch;
import com.otd.onetoday_back.weather.location.model.LocationDto;
import com.otd.onetoday_back.weather.location.model.PostAddressReq;
import com.otd.onetoday_back.weather.location.model.PutTitleReq;
import com.otd.onetoday_back.weather.location.model.SearchDto;
import com.otd.onetoday_back.weather.location.model.json.VWorldResponse;
import com.otd.onetoday_back.weather.util.GeoTrans;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {
    private final SearchFeignClient searchFeignClient;
    private final ConstSearch constSearch;
    private final ObjectMapper objectMapper;
    private final LocationMapper locationMapper;

    public List<SearchDto> search(String keyword) throws Exception {

        String result = searchFeignClient.searchLocation(
                constSearch.key,
                constSearch.service,
                constSearch.request,
                "place",
                keyword,
                constSearch.format,
                1,
                100
        );

        VWorldResponse response = objectMapper.readValue(result, VWorldResponse.class);
        List<VWorldResponse.VWorldItem> items = null;
        if (response.getResponse() != null && response.getResponse().getResult() != null) {
            items = response.getResponse().getResult().getItems();
        }

        if (items == null || items.isEmpty()) {
            String result2 = searchFeignClient.searchAddress(
                    constSearch.key,
                    constSearch.service,
                    constSearch.request,
                    "address",
                    constSearch.category,
                    keyword,
                    constSearch.format,
                    1,
                    100
            );
            VWorldResponse response2 = objectMapper.readValue(result2, VWorldResponse.class);
            items = response2.getResponse().getResult().getItems();
            log.info(items.toString());
        }

        List<SearchDto> list = new ArrayList<>();
        Set<String> uniqueCheck = new HashSet<>();

        for (VWorldResponse.VWorldItem item : items) {
            String road = item.getAddress().getRoad();
            String parcel = item.getAddress().getParcel();

            if (road == null || road.isBlank() || parcel == null || parcel.isBlank()) {
                continue;
            }
            String unique = road.replace(" ","") + "|" + parcel.replace(" ","");
            if (uniqueCheck.contains(unique)) {
                continue;
            }
                uniqueCheck.add(unique);

            if (item.getTitle() != null) {
                list.add(SearchDto.builder()
                        .title(item.getTitle())
                        .road(item.getAddress().getRoad())
                        .parcel(item.getAddress().getParcel())
                        .lat(Double.parseDouble(item.getPoint().getY()))
                        .lon(Double.parseDouble(item.getPoint().getX()))
                        .build());
            }
            if (item.getAddress().getBldnm() == null ||
                        item.getAddress().getBldnm().isBlank()) {
                list.add(SearchDto.builder()
                        .title(item.getAddress().getRoad())
                        .road(item.getAddress().getRoad())
                        .parcel(item.getAddress().getParcel())
                        .lat(Double.parseDouble(item.getPoint().getY()))
                        .lon(Double.parseDouble(item.getPoint().getX()))
                        .build());
            } else {
            list.add(SearchDto.builder()
                    .title(item.getAddress().getBldnm())
                    .road(item.getAddress().getRoad())
                    .parcel(item.getAddress().getParcel())
                    .lat(Double.parseDouble(item.getPoint().getY()))
                    .lon(Double.parseDouble(item.getPoint().getX()))
                    .build());
            }
        }
        return list;
    }

    public void saveAddress(PostAddressReq req) {

        GeoTrans.LatXLngY grid = GeoTrans.convertGRID_GPS(req.getLat(), req.getLon());
        req.setNx(grid.nx);
        req.setNy(grid.ny);
        int e = locationMapper.existsAddress(req);
        if (e > 0) {
            throw new IllegalStateException("이미 저장된 주소입니다.");
        }
        locationMapper.insertAddress(req);
    }

    public List<PostAddressReq> getAddressList(int memberId) {
        return locationMapper.addressList(memberId);
    }

    public  int removeAddress(int id, int memberId) {
        return locationMapper.deleteAddress(id, memberId);
    }

    @Transactional
    public void selectAddress(int memberId, int addressId) {
        int clear = locationMapper.clearSelectedAddress(memberId);
        int update = locationMapper.updateSelectedAddress(memberId, addressId);
        if (update == 0){
            throw new IllegalStateException("수정 실패");
        }
    }

    public LocationDto getSelectedAddress(int memberId) {
        return locationMapper.findSelectedAddressByMemberId(memberId);
    }

    public int modifyTitle(PutTitleReq req) {
        return locationMapper.modifyAddress(req);
    }
}
