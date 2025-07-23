package com.otd.onetoday_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

// api
@ConfigurationPropertiesScan
@EnableFeignClients
@SpringBootApplication
public class OneToDayBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneToDayBackApplication.class, args);
    }

}
