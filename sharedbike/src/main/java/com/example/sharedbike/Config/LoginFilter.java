//package com.example.sharedbike.Config;
//
//import com.example.sharedbike.entity.Admin;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.util.AntPathMatcher;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class LoginFilter extends OncePerRequestFilter {
//    // 定义白名单URL
//    private static final List<String> WHITELIST = Arrays.asList(
//            "/login",
//            "/login.html",
//            "/static/**" // 允许访问静态资源
//    );
//    private final AntPathMatcher pathMatcher = new AntPathMatcher();
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String requestURI = request.getRequestURI();
//        String method = request.getMethod();
//
//        // 检查请求的URI是否在白名单中
//        if (isWhitelisted(requestURI) || isLoginPageWithSession(requestURI)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // 允许已登录用户的请求
//        Admin user = (Admin) request.getSession().getAttribute("user");
//        if (user != null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        //未登录的话直接跳转的登陆页面
//        response.sendRedirect("/login.html");
//
//        // 对于其他请求，确保用户已登录
////        response.setContentType("application/json;charset=utf-8");
////        PrintWriter out = response.getWriter();
////        out.write("{\"message\": \"请先登录\"}");
////        out.flush();
////        out.close();
//
//    }
//
//    // 检查URI是否在白名单中
//    private boolean isWhitelisted(String uri) {
//        return WHITELIST.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri));
//    }
//
//    // 检查是否是包含JSESSIONID的登录页面
//    private boolean isLoginPageWithSession(String uri) {
//        return uri.startsWith("/login.html") && uri.contains(";JSESSIONID=");
//    }
//}
