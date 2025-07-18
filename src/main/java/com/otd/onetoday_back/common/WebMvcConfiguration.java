package com.otd.onetoday_back.common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
@Configuration //빈등록
public class WebMvcConfiguration implements WebMvcConfigurer {
// 아래는 파일 경로 설정 시 사용
        private final String uploadPath;

    public WebMvcConfiguration(@Value("${constants.file.directory}") String uploadPath) {
        this.uploadPath = uploadPath;
        log.info("Upload Path: {}", uploadPath);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+uploadPath);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

}
