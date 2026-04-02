package com.stu.helloserver.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行 OPTIONS 预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String uri = request.getRequestURI();
        String method = request.getMethod();

        // 放行注册和登录接口（双重保险）
        if (("/api/users".equals(uri) && "POST".equalsIgnoreCase(method)) ||
                ("/api/users/login".equals(uri) && "POST".equalsIgnoreCase(method))) {
            return true;
        }

        // 获取并验证 Token
        String token = request.getHeader("Authorization");
        System.out.println("请求 URI: " + uri);
        System.out.println("Authorization 头: " + token);   // 调试日志

        if (token == null || token.isEmpty()) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            response.getWriter().write("{\"code\":401,\"msg\":\"登录凭证已缺失，请重新登录\"}");
            return false;
        }

        // 可选：如果前端发送 "Bearer xxx"，可以剥离前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 这里可以添加 token 有效性校验（例如 JWT 解析），暂时只要有值就放行
        return true;
    }
}