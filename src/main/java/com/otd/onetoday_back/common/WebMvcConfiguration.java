package com.otd.onetoday_back.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Slf4j
@Configuration //빈등록
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final String uploadPath;
    @Value("${constants.file.directory}")
    private String uploadDir;

    public WebMvcConfiguration(@Value("${constants.file.directory}") String uploadPath) {
        if (uploadPath.endsWith("/") || uploadPath.endsWith("\\")) {
            uploadPath = uploadPath.substring(0, uploadPath.length() - 1);
        }
        this.uploadPath = uploadPath;
        log.info("[WebMvcConfiguration] Upload Base Path = {}", this.uploadPath);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 커뮤니티 첨부 이미지: /files/community/** → {uploadPath}/community/**
        //   (FileStorageService가 {uploadPath}/community/{postId}/{uuid.ext}로 저장한다고 가정)
        registry.addResourceHandler("/files/community/**")
                .addResourceLocations("file:" + uploadPath + "/community/");

        // memo, diary 첨부 이미지
        registry.addResourceHandler("/api/OTD/memoAndDiary/memo/image/**")
                .addResourceLocations("file:///" + uploadDir + "/memo/");

        registry.addResourceHandler("/api/OTD/memoAndDiary/diary/image/**")
                .addResourceLocations("file:///" + uploadDir + "/diary/");

        // 기존 일기 탭 등에서 쓰던 경로 유지: /pic/** → {uploadPath}/
        registry.addResourceHandler("/pic/**")
                .addResourceLocations("file:" + uploadPath + "/");

        //  더 구체적인 패턴(/files/**, /pic/**)이 우선 매칭되므로 충돌 없음
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource resource = location.createRelative(resourcePath);
                        if (resource.exists() && resource.isReadable()) {
                            return resource;
                        }
                        return new ClassPathResource("/static/index.html");
                    }
                });
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}