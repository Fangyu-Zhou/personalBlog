package com.rain.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*继承spring框架中的interceptor（过滤器）*/
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /*在请求未到达之前进行预处理*/
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        /*检查session中的参数user是否为空，为空就说明没有进行登陆*/
        if(request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
