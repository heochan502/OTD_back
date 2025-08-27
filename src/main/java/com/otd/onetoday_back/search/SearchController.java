package com.otd.onetoday_back.search;


import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/api/OTD/search")
@Slf4j
public class SearchController {
    private final String clientId = "nRByUX17IdlppTNDeIkP";
    private final String clientSecret = "FAk_eGgT8p";

    @GetMapping("/local")
    public ResponseEntity<String> searchLocal(@RequestParam String inputSearchText) {
        log.info("찾는 검색어 {}",inputSearchText);
//        String searchText = null;
//        String searchLocal = inputSearchText;
//        try {
//            searchText = URLEncoder.encode(inputSearchText, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException("검색어 인코딩 실패",e);
//        }
//        String apiURL = "https://openapi.naver.com/v1/search/local?query=" + searchText;    // JSON 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ searchText; // XML 결과
        URI apiURL = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com") // 여기수정하면 블로그 나 뭐 다른거 다 검색 가능할듯
                .path("/v1/search/local")
                .queryParam("query", inputSearchText)
                .queryParam("total", 30) // 검색api는 별짓을 다해도 소용없이 5개 제한임
                .queryParam("start", 20)
                .queryParam("sort", "sim") //정확도순 내림 차순
                .queryParam("display", 20) // 데이터 갯수 100개 까지 가능하다던데..
                .encode()
                .build()
                .toUri();
//        log.info("apiURL2 {}",apiURL2);
//        log.info("apiURL {}",apiURL);

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        // 서비스 분리
        String responseBody = get(apiURL,requestHeaders);


        log.info("결과값  {}", responseBody);
        return ResponseEntity.ok(responseBody);
    }

    private static  String get(URI apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }
    private static HttpURLConnection connect(URI apiUrl){
        try {
            URL url = apiUrl.toURL();
//            URL url =  new URL(apiUrl);

            log.info("url {}",url);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static  String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);
        log.info("streamReader {}",streamReader);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

}
