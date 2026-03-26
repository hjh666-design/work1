package com.stu.helloserver.config; // 注意包路径要与您的项目一致

import com.stu.helloserver.interceptor.AuthInterceptor; // 导入上面创建的拦截器
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 核心注解，表明这是一个配置类
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor()) // 1. 注册自定义拦截器
                .addPathPatterns("/api/**") // 2. 指定拦截路径：所有以`/api/`开头的请求
                .excludePathPatterns( // 3. 指定排除路径（放行）：
                        "/api/users/login",  // 放行登录接口
                        "/api/users"   // 放行用户注册（新增）接口

                );
    }
}