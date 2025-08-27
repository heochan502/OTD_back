package com.otd.onetoday_back.community.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${app.upload.base-dir}")
    private String baseDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // http://<host>/files/community/** → 로컬 D:/uploads/otd/community/**
        registry.addResourceHandler("/files/community/**")
                .addResourceLocations("file:" + baseDir + "/");
    }
}
