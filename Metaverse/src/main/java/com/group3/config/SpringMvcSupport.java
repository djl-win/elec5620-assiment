package com.group3.config;

import com.group3.interceptor.UserLoginVerifyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcSupport implements WebMvcConfigurer {

    @Autowired
    private UserLoginVerifyInterceptor userLoginVerifyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginVerifyInterceptor)
                .addPathPatterns("/pages/**")
                .excludePathPatterns("/pages/mIndex.html","/pages/sign.html");
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/nftImages/**").addResourceLocations("file:D:/nftImages/");
//    }
}
