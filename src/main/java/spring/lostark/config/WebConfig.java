package spring.lostark.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 해당 설정만 해두면.. URI PATH 이하는 모두 로컬에서 파일이 가져와짐
 * location = 로컬 경로 주소(실제 이미지가 있는 폴더 주소) pwd로 확인 가능
 * uri_path = http uri 주소 나는 /upload로 해놨기에 http://localhost:8080/upload/** 이하 이미지가 인식됨
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${resources.location}")
    private String resourcesLocation;
    @Value("${resources.uri_path:}")
    private String resourcesUriPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcesUriPath + "/**")
                .addResourceLocations("file://" + resourcesLocation)
                .resourceChain(true)
                .addResolver(new utf8DecodeResourceResolver());


    }

}
