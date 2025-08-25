package com.otd.onetoday_back.search;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final String clientId = "Re_XCHw41xJTGd9CYgOb";
    private final String clientSecret = "oF286Gg9ZJ";

    @GetMapping("/local")
    public ResponseEntity<String> searchLocal(@RequestParam String query) {
        String apiUrl = "https://openapi.naver.com/v1/search/local?query=" + URLEncoder.encode(query, StandardCharsets.UTF_8);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

}
