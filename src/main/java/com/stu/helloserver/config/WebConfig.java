package com.stu.helloserver.config;

import com.stu.helloserver.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;   // 注入 Spring 管理的 Bean

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")          // 只拦截 /api/ 下的请求
                .excludePathPatterns(
                        "/api/users/login",          // 登录接口放行
                        "/api/users"                 // 注册接口放行（POST）
                );
    }
}