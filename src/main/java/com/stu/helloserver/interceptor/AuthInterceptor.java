package com.stu.helloserver.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.IOException;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 尝试从HTTP请求头中获取名为“Authorization”的令牌
        String token = request.getHeader("Authorization");

        // 2. 判断令牌是否存在或为空
        if (token == null || token.isEmpty()) {
            // 3. 如果令牌缺失，拦截请求，返回401 JSON
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401); // 设置HTTP状态码为401
            // 构造错误JSON
            String errorJson = "{\"code\": 401, \"msg\": \"登录凭证已缺失,请重新登录\"}";
            response.getWriter().write(errorJson);
            return false; // 返回false，表示拦截，不放行到Controller
        }
        // 4. 令牌存在，允许请求继续执行
        return true;
    }
}